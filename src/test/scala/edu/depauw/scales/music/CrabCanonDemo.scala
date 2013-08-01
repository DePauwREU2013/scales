package edu.depauw.scales.music

import edu.depauw.scales.Director
import edu.depauw.scales.Instrument
import edu.depauw.scales.Octave
import edu.depauw.scales.Tempo

/**
 * This demo shows how to use the reverse method to play Bach's Crab Canon.
 */
object CrabCanonDemo extends App {
  
  val bar1 = C.hn + E.ft.hn + G.hn + A.ft.hn +
		  	 B.<.hn + Rest(1) + G.hn + F.sh.hn + F.hn +
		  	 E.hn + E.ft.hn + D + D.ft + C
  val bar2 = B.< + G.< + C + F + E.ft.hn + D.hn +
  			 C.hn + E.ft.hn + G.en + F.en + G.en + C.>.en + G.en + E.ft.en + D.en + E.ft.en + 
  			 F.en + G.en + A.en + B.en + C.>.en + E.ft.en + F.en + G.en +
  			 A.ft.en + D.en + E.ft.en + F.en + G.en + F.en + E.ft.en + D.en
  val bar3 = E.ft.en + F.en + G.en + A.ft.en + B.ft.en + A.ft.en + G.en + F.en +
  			 G.en + A.ft.en + B.ft.en + C.>.en + D.ft.>.en + B.ft.en + A.ft.en + G.en +
  			 A.en + B.en + C.>.en + D.>.en + E.ft.>.en + C.>.en + B.en + A.en +
  			 B.en + C.>.en + D.>.en + E.ft.>.en + F.>.en + D.>.en + G.en + D.>.en +
  			 C.>.en + D.>.en + E.ft.>.en + F.>.en + E.ft.>.en + D.>.en + C.>.en + B.en +
  			 C.> + G + E.ft + C
  			 
  val part = Tempo(100, bar1 + bar2 + bar3)
  val flute = Instrument((0,73), Octave(5, part))
  val bassoon = Instrument((0, 72), part.reverse)
  
  // try listening to them one at a time first
  (new Director(flute | bassoon)).start
}