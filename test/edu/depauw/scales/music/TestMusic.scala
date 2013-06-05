package edu.depauw.scales.music

import edu.depauw.scales._

import javax.sound.midi.MidiSystem

object DemoMusic extends Application {
  //import Duration._

    val synth = MidiSystem.getSynthesizer()
    synth.open()
    val insts = synth.getAvailableInstruments()
    for (i <- insts) println(i)
    synth.close()
  
  /*private val test = C(4, QN) + D(4, QN) + C(4, HN) * E(4, HN)*/
    //C(5, EN)
    
    //private val test = C * E * G

  //  private val test = C~.5 + D~.5 + E~.5 + F + G + A + B + C\5 + D\5 + E\5 + F\5 +
    //  (C\5 | E\5 | G\5)~2
    
//    private val test = Volume(127, C~.5 + D~.5 + E~.5 + F + G + A + B + C\5 + D\5 + E\5 + F\5 +
  //    (C\5 | E\5 | G\5)~2)
    
  //  private val test = Volume(40, C~.5 + D~.5 + E~.5 + F + G + A + B + C\5 + D\5 + E\5 + F\5 +
  //    (C\5 | E\5 | G\5)~2)

     // private val test = Tempo(70, C + D + E + F + G + A + B + C.> + B + A + G + F + E + D + C)
      
    //private val test = C + D + E + F + G + A + B + C.> + D.> + E.> + F.>
    //private val test = C + C.s + D + E.f + E + F + F.s + G + A.f + A + A.s + B + B.s
    //private val test = C + D + E + F + G + A + B
  
  //  private val director = new Director(test)
  
    //director.start()
}
