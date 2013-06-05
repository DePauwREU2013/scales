package edu.depauw.scales.music;

import edu.depauw.scales._

import javax.sound.sampled.{Clip => JClip, AudioSystem, DataLine}

import java.io.File

/*
 * Constructor takes the absolute pathname of the audio file.
 * If count is 0, loop clip as needed for duration;
 * otherwise, loop clip count times (possibly beyond duration)
 */
case class Clip(count : Int, f : String) extends Step {  
  val stream = AudioSystem.getAudioInputStream(new File(f))
  val numBytes = stream.getFrameLength.toInt * stream.getFormat.getFrameSize
  val format = stream.getFormat
  val info = new DataLine.Info(classOf[JClip], format)
  
  val buffer = new Array[Byte](numBytes)
  stream.read(buffer)
  stream.close()
  for (i <- 1000 until 1025) {
    println(buffer(2 * i) + ", " + buffer(2 * i + 1))
  }
  println(format)
  
  val beats = 1.0
  
  def act(actor : StepActor) {
    val clip = AudioSystem.getLine(info).asInstanceOf[JClip]

    clip.open(format, buffer, 0, numBytes)
    if (count == 0) {
      clip.loop(JClip.LOOP_CONTINUOUSLY)
      actor.director ! Request(actor, actor.startTime + actor.duration, Done)
    } else {
      clip.loop(count - 1)
      actor.director ! Request(actor, actor.startTime + clip.getMicrosecondLength / 1000, Done)
    }

    actor.director ! Request(actor.parent, actor.startTime + actor.duration, Done)
    
    actor.react {
      case Done => {
        clip.stop()
        clip.close()
      }
    }
  }
}

