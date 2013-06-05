package edu.depauw.scales.music;

import edu.depauw.scales._

object TestSynth extends Application {
  val director = new Director(A + Synth(440, Util.sin2pi) + A)
  director.start()
}
