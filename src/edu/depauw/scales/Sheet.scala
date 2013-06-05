package edu.depauw.scales

import java.awt.geom.AffineTransform
import javax.sound.midi.MidiChannel;

class Sheet {
  val msecPerBeatOpt : Option[Double] = None
  val endTimeFunOpt : Option[StepActor => Long] = None
  val velocityOpt : Option[Int] = None
  val channelOpt : Option[MidiChannel] = None
  val instrumentOpt : Option[(Int,Int)] = None
  val middleCOpt : Option[Int] = None
  val fpsOpt : Option[Double] = None
  def transformOptFun(parent : Context) : Option[AffineTransform] = None
}

object Pedal {
  def apply(step : Step) =
    Style(new Sheet {
            override val endTimeFunOpt = Some({actor : StepActor => actor.parent.endTime})
          }, step)
}

object NoPedal {
  def apply(step : Step) =
    Style(new Sheet {
            override val endTimeFunOpt = Some({actor : StepActor => actor.startTime + actor.duration})
          }, step)
}

object Volume {
  def apply(velocity : Int, step : Step) =
    Style(new Sheet {
            override val velocityOpt = Some(velocity)
            }, step)          
}

object Tempo {
  def apply(bpm : Int, step : Step) =
    Style(new Sheet {
            override val msecPerBeatOpt = Some(60000.0 / bpm)
            }, step)
}

// TODO this affects the duration calculation...

object Instrument {
  def apply(i : (Int,Int), step : Step) =
    Style(new Sheet {
            override val instrumentOpt = Some(i)
            }, step)
}

object Octave {
  def apply(octave : Int, step : Step) =
    Style(new Sheet {
            override val middleCOpt = Some(12 * (octave + 1))
          }, step)
}

object FPS {
  def apply(fps : Double, step : Step) =
    Style(new Sheet {
            override val fpsOpt = Some(fps)
          }, step)
}

object TransformSheet {
  def apply(transform : AffineTransform, step : Step) =
    Style(new Sheet {
            override def transformOptFun(parent : Context) = {
              val t = new AffineTransform(parent.transform)
              t.concatenate(transform)
              Some(t)
            }
          }, step)
}

// TODO Transpose(steps : Int, step : Step) -- implemented like TransformSheet, to adjust middleC
