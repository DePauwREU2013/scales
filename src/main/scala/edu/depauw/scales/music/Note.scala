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
  
  def reverse = this
  
  
  /** Function indicate sharp note of key
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def sh = Note(this.pitch + 1, this.beats)  
  
  /** Function indicate flat note of key
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def ft = Note(this.pitch - 1, this.beats)
  
  /* How should we deal with possible errors if these methods are called
  	on a Note with a pitch that will put it out of Midi range?
  */
  
  /** Function for moving a key up by one octave
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def >    = Note(this.pitch + 12, this.beats)
  
  /** Function for moving a key up by two octaves
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def >>   = Note(this.pitch + 24, this.beats)
  
  /** Function for moving a key up by three octaves
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def >>>  = Note(this.pitch + 36, this.beats)
  
  /** Function for moving a key up by four octaves
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def >>>> = Note(this.pitch + 48, this.beats)
  
  /** Function for moving a key down by one octave
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def <    = Note(this.pitch - 12, this.beats)
  
   /** Function for moving a key down by two octaves
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def <<   = Note(this.pitch - 24, this.beats)
  
    /** Function for moving a key down by three octaves
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def <<<  = Note(this.pitch - 36, this.beats)
  
    /** Function for moving a key down by four octaves
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def <<<< = Note(this.pitch - 48, this.beats)
  
  override def ~(dur : Double) = Note(this.pitch, dur)
      
   /** Function to indicate note duration as a whole note
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def wn = Note(this.pitch, 4)
  
   /** Function to indicate note duration as a half note
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def hn = Note(this.pitch, 2)
  
   /** Function to indicate note duration as an eighth note
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def en = Note(this.pitch, 0.5)
  
    /** Function to indicate note duration as a sixteenth note
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def sn = Note(this.pitch, 0.25)
  
      /** Function to indicate dotted note
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def dot = Note(this.pitch, this.beats * 3 / 2)
  
      /** Function to indicate note triplet
  *  
  *  @param nil
  *  @return Note
  * 
  */
  def triplet = Note(this.pitch, this.beats * 2 / 3)
  
  
  /** Function to indicate pianississimo volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def ppp = Volume(10, this)
  
  /** Function to indicate pianissimo volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def pp  = Volume(20, this)
  
  /** Function to indicate piano volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def p   = Volume(30, this)
  
  /** Function to indicate mezzo piano volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def mp  = Volume(50, this)
  
  /** Function to indicate mezzo forte volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def mf  = Volume(70, this)
  
  /** Function to indicate forte volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def f   = Volume(90, this)
  
  /** Function to indicate fortissimo volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
  def ff  = Volume(105, this)
  
  /** Function to indicate fortississimo volume
  *  
  *  @param nil
  *  @return Volume
  * 
  */
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
  
  def reverse = this
}
