package lila.pref

import play.api.data.*
import play.api.data.Forms.*

object PrefSingleChange:

  case class Change[A](field: String, mapping: Mapping[A], update: A => Pref => Pref):
    def form: Form[A] = Form(single(field -> mapping))

  private def changing[A](field: PrefForm.fields.type => (String, Mapping[A]))(
      f: A => Pref => Pref
  ): Change[A] =
    Change(field(PrefForm.fields)._1, field(PrefForm.fields)._2, f)

  val changes: Map[String, Change[?]] = List[Change[?]](
    changing(_.theme): v =>
      _.copy(theme = v),
    changing(_.pieceSet): v =>
      _.copy(pieceSet = v),
    changing(_.theme3d): v =>
      _.copy(theme3d = v),
    changing(_.pieceSet3d): v =>
      _.copy(pieceSet3d = v),
    changing(_.is3d): v =>
      _.copy(is3d = v),
    changing(_.soundSet): v =>
      _.copy(soundSet = v),
    changing(_.zen): v =>
      _.copy(zen = v),
    changing(_.voice): v =>
      _.copy(voice = v.some),
    changing(_.keyboardMove): v =>
      _.copy(keyboardMove = v | Pref.KeyboardMove.NO),
    changing(_.autoQueen): v =>
      _.copy(autoQueen = v)
  ).map: change =>
    change.field -> change
  .toMap
