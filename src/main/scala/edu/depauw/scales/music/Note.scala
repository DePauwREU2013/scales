package edu.depauw.scales.music

import edu.depauw.scales._

case class Note(pitch : Int, beats : Double) extends Step {
  def act(actor : StepActor) {
    val truePitch = actor.context.middleC + pitch
    val instrument = actor.context.instrument
    val channel = actor.context.channel
    channel synchronized {
      channel.programChange(instrument._1, instrument._2)
      channel.noteOn(truePitch, actor.context.velocity)
    }
    
    // Request the local Done message 10 msec before the parent's so that all
    // of the noteOff requests should happen before any following noteOns
    actor.director ! Request(actor, actor.endTime - 10, Done)
    actor.director ! Request(actor.parent, actor.startTime + actor.duration, Done)
    actor.react {
      case Done => channel synchronized {channel.noteOff(truePitch)}
    }
  }
  
  def sh = Note(this.pitch + 1, this.beats)  
  def ft = Note(this.pitch - 1, this.beats)
  
  /* How should we deal with possible errors if these methods are called
  	on a Note with a pitch that will put it out of Midi range?
  */
  def >    = Note(this.pitch + 12, this.beats)
  def >>   = Note(this.pitch + 24, this.beats)
  def >>>  = Note(this.pitch + 36, this.beats)
  def >>>> = Note(this.pitch + 48, this.beats)
  def <    = Note(this.pitch - 12, this.beats)
  def <<   = Note(this.pitch - 24, this.beats)
  def <<<  = Note(this.pitch - 36, this.beats)
  def <<<< = Note(this.pitch - 48, this.beats)
  
  override def ~(dur : Double) = Note(this.pitch, dur)
      
  def wn = Note(this.pitch, 4)
  def hn = Note(this.pitch, 2)
  def en = Note(this.pitch, 0.5)
  def sn = Note(this.pitch, 0.25)
  def dot = Note(this.pitch, this.beats * 3 / 2)
  def triplet = Note(this.pitch, this.beats * 2 / 3)
  
  
  def ppp = Volume(10, this)
  def pp  = Volume(20, this)
  def p   = Volume(30, this)
  def mp  = Volume(50, this)
  def mf  = Volume(70, this)
  def f   = Volume(90, this)
  def ff  = Volume(105, this)
  def fff = Volume(120, this)
  
}

object C extends Note(0, 1)
object D extends Note(2, 1)
object E extends Note(4, 1)
object F extends Note(5, 1)
object G extends Note(7, 1)
object A extends Note(9, 1)
object B extends Note(11, 1)

case class Rest(val beats : Double) extends Step {
  def act(actor : StepActor) {
    actor.director ! Request(actor.parent, actor.startTime + actor.duration, Done)
  }
}
