package edu.depauw.scales.music

import edu.depauw.scales._
import java.io.File
import javax.sound.midi.Patch


object GoldbergDemo extends App {
  
  /*
 * Auflösung No.1
 */
  val bar1 = G.<< + D.< + C.< + B.<< + D.< + E.< + F.sh.< + G.<
  val A1 = bar1 | bar1.reverse
  
  
  /*
 * Auflösung No.2
 */
  
  val bar2 = D + G.< + A.< + B.< + G.< + F.sh.< + E.< + D.<
  val A2 = bar2 | bar2.reverse

  val sompopFalsetto = new File(System.getProperty("user.dir") + "/resources/sompop.wav")
  
  val director = new Director(
      bar1.reverse + Rest(4) +
      bar1 + Rest(4) +
      A1 + Rest(4) +
      Instrument((0,127), Volume(120,A1))
    )
  
  director.loadInstrument(sompopFalsetto, new Patch(0,127))
  
  director.start

}