package edu.depauw.scales.music

import edu.depauw.scales._
import scala.util.Random

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
  
  def canonRandomInstrument(part: Step, voices: Int, entranceOffset: Double = 4): Step = {
    (1 to (voices-1)).foldLeft(part)((group, n) => {
    	(Instrument((0, Random.nextInt(128)), part) + Rest(entranceOffset)) |
    	    (Rest(entranceOffset) + group)
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
  
  // Haydn - Erstes Gebot
  
  val hegLine1 = E.>.wn + 
  				 D.>.hn + E.>.hn +
  				 F.>.hn + E.> + D.> +
  				 C.>.wn + 
  				 D.>.hn + E.>.hn +
  				 D.>.wn
  val hegLine2 = C.>.wn + 
  				 B.hn + C.>.hn +
  				 D.>.hn + C.> + B +
  				 A.wn + 
  				 B.hn + C.>.hn +
  				 B.wn
  val hegLine3 = C.wn +
  				 G.hn + F + E +
  				 D.hn + E.hn +
  				 F.wn +
  				 E + D + C + E +
  				 G.wn
  
  val hErstesGebot = Tempo(180, (hegLine1 + hegLine2 + hegLine3))
  
  val song = canon(hErstesGebot, 3, 8)
  
  (new Director(song)).start
  
}