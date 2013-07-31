package edu.depauw.scales.music;

import edu.depauw.scales._

object TestSynth extends App {
  def sin2Pi(x: Double): Double = math.sin(2 * math.Pi * x)
  
  val director = new Director(A + Synth(440, sin2Pi) + A)
  director.start()
}
