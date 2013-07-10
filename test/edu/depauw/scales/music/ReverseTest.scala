package edu.depauw.scales.music

import edu.depauw.scales.Director
import edu.depauw.scales.Para
import edu.depauw.scales.Instrument

object ReverseTest extends App {
  val scale = (C + D + E + F + G + A + B)
  val scale2 = (E + F + G + A + B + C.> + D.>)
  val scale3 = Para(scale2,scale)
  
  private val violin = (0,40)
   
  val scale4 = Instrument(violin, scale)
  
  val director = new Director(scale4.reverse)
  
  director.start
}