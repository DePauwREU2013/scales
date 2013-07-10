package edu.depauw.scales.music

import edu.depauw.scales._

object CanonTest extends App {
  
  /*
   * given a musical line, this method will "canon-ize" it using the
   * number of voices provided, and the offset (given in beats) between
   * entrances of new voices
   */
  def canon(part: Step, voices: Int, entranceOffset: Double = 4): Step = {
    (1 to (voices-1)).foldLeft(part)((group, n) => {
    	part + Rest(entranceOffset) | Rest(entranceOffset) + group
    })
  }
  
  // Row, Row, Row Your Boat...
  val r1 = C + C + C~.75 + D~.25 + E
  val r2 = E~.75 + D~.25 + E~.75 + F~.25 + G~2
  val r3 = C.>.en.triplet + C.>.en.triplet + C.>.en.triplet + 
           G.en.triplet   + G.en.triplet   + G.en.triplet   + 
           E.en.triplet   + E.en.triplet   + E.en.triplet   + 
  		   C.en.triplet   + C.en.triplet   + C.en.triplet
  val r4 = G~.75 + F~.25 + E~.75 + D~.25 + C~2
  
  val rrrBoat = r1 + r2 + r3 + r4
  
  val song = canon(rrrBoat, 4, 4)
  
  (new Director(song)).start
  
}