package edu.depauw.scales.music

import edu.depauw.scales._


object GoldbergDemo extends App {
  
  /*
 * Auflosungen No.1
 */
  val bar1 = G.<< + D.< + C.< + B.<< + D.< + E.< + F.sh.< + G.<
  val A1 = Tempo(100, (bar1 | bar1.reverse))
  
  
  /*
 * Auflosungen No.2
 */
  
  val bar2 = D + G.< + A.< + B.< + G.< + F.sh.< + E.< + D.<
  val A2 = Tempo(100, (bar2| bar2.reverse))

  (new Director(A2)).start
  

}