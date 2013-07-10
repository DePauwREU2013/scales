package edu.depauw.scales.music

import edu.depauw.scales._

import javax.sound.sampled.{Clip => JClip, AudioSystem, DataLine, AudioFormat}

case class Synth(freq : Double, fn : Double => Double) extends Step {
  val beats = 1.0
  
  val sampleRate = 22050f
  val bytesPerSample = 2
  val maxSample = (1 << (8 * bytesPerSample - 1)) - 1
  val numSamples = sampleRate.toInt // generate one second
  val numBytes = bytesPerSample * numSamples
  val format = new AudioFormat(sampleRate, 8 * bytesPerSample, 1, true, false)
  val info = new DataLine.Info(classOf[JClip], format)
  
  val buffer = new Array[Byte](numBytes)
  
  for (i <- 0 until numSamples) {
    val t = (i * freq / numSamples) % 1.0
    val amp = (maxSample * fn(t)).toInt
    
    // this assumes bytesPerSample == 2, little-endian
    buffer(2 * i) = (amp & 0xff).toByte
    buffer(2 * i + 1) = (amp >> 8).toByte
  }
  
  // TODO incorporate volume from actor.context.velocity
  def act(actor : StepActor) {
    val clip = AudioSystem.getLine(info).asInstanceOf[JClip]

    clip.open(format, buffer, 0, numBytes)
    clip.loop(JClip.LOOP_CONTINUOUSLY)
    actor.director ! Request(actor, actor.startTime + actor.duration, Done)

    actor.director ! Request(actor.parent, actor.startTime + actor.duration, Done)
    
    actor.react {
      case Done => {
        clip.stop()
        clip.close()
      }
    }
  }
  
  def reverse = this
  
}
