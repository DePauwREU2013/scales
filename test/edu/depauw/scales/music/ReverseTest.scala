package edu.depauw.scales.music

import edu.depauw.scales._

object ReverseTest extends App {
  val scale = (C + D + E + F + G + A + B + C.>)
  val scale2 = (E + F + G + A + B + C.> + D.> + E.>)
  val scale3 = Para(scale2,scale)
  
  val violin = (0,40)
   
  val scale4 = Instrument(violin, scale3)
  
  val director = new Director(scale4.reverse)
  
  director.start
}