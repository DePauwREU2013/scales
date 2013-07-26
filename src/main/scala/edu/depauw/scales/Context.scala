package edu.depauw.scales

import java.awt.geom.AffineTransform
import javax.sound.midi.MidiChannel;

trait Context {
   /** Milliseconds per beat
   *  @return milliseconds per beat
   */
  val msecPerBeat : Double
  
  val endTimeFun : StepActor => Long
  
  /** Velocity
   *  @return velocity
   */
  val velocity : Int
  
  /** Channel
   *  @return channel
   */
  val channel : MidiChannel
  
  /** Instrument
   *  @return instrument
   */
  val instrument : (Int,Int)
  
  /** Middle C music key
   *  @return Middle C music key
   */
  val middleC : Int
  
  /** Frames per second
   *  @return frames per second
   */
  val fps : Double
  val transform : AffineTransform
  
  def +(sheet : Sheet) : Context = {
    new SheetContext(this, sheet)
  }
}

class BaseContext(director : Director) extends Context {
  val msecPerBeat = 1000.0
  val endTimeFun = {actor : StepActor => actor.startTime + actor.duration}
  val velocity = 64
  val channel = director.synth.getChannels()(0)
  val instrument = (0,0)
  val middleC = 60
  val fps = 20.0
  val transform = new AffineTransform()
}

class SheetContext(parent : Context, sheet : Sheet) extends Context {
  val msecPerBeat = sheet.msecPerBeatOpt.getOrElse(parent.msecPerBeat)
  val endTimeFun = sheet.endTimeFunOpt.getOrElse(parent.endTimeFun)
  val velocity = sheet.velocityOpt.getOrElse(parent.velocity) 
  val channel = sheet.channelOpt.getOrElse(parent.channel)
  val instrument = sheet.instrumentOpt.getOrElse(parent.instrument)
  val middleC = sheet.middleCOpt.getOrElse(parent.middleC)
  val fps = sheet.fpsOpt.getOrElse(parent.fps)
  val transform = sheet.transformOptFun(parent).getOrElse(parent.transform)
}
