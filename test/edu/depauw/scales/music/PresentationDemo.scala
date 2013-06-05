package edu.depauw.scales.music

import edu.depauw.scales._

object PresentationDemo extends App {
  /*Bach Chorale BWV 256
    Harmony by Margaret Greentree, www.jsbchorales.net*/
  
  private val soprano1 = Octave(5, C + C + (A.< + B.< + C + D)~2 + E + (D~2 + E + F)~1 + C)
  private val alto1 = E~.5 + F~.5 + G + F + G +G + A + G~.5 + F~.5 + E
  private val tenor1 = C + C + C + C + C + C + B.< + G.<
  private val bass1a = Octave(3, A + E + F + E~.5 + D~.5 + C + F + G + C)
  private val bass1  = Octave(3, A + E + F + E~.5 + D~.5 + C + F + G + C)
  
  private val soprano2 = C.> + B + A~.5 + B~.5 + C.> + D.> ~.5 + C.> ~.5 + B~2 + A
  private val alto2 = F.sh + G + F + E + A + A + G.sh + E
  private val tenor2 = A.< + D + D + C~.5 + B.< ~.5 + A + F + E~.5 + D~.5 + C.sh
  private val bass2 = Octave(3, A + G + D.> + A~.5 + G~.5 + F~.5 + E~.5 + D + E + A.<)
  
  private val soprano3 = Octave(5, C + C + (A.< + B.< + C + D)~2 + E + (D + E~.5 + F~.5)~1 + D + C)
  private val alto3 = (E + F)~1 + G + F + G + G + A + (G + F)~1 + E
  private val tenor3 = A.< + C + C + C + C + C + B.< + G.<
  private val bass3 = Octave(3, A + E + F + (E + D)~1 + C + F + G + C)
  
  private val soprano4 = C.> + B + (A + B)~1 + C.> + (D.> + C.>)~1 + B~2 + A
  private val soprano5 = Octave(5, D + E + E + (B.< + C)~1 + D + C + C + B.<)
  private val soprano6 = C.> + D.> + (A + B)~1 + C.> + B + (A~.5 + B~.5 + C.>)~1 + A + G
  private val soprano7 = C.> + B + (A + B)~1 + C.> + (D.> + C.>)~1 + B~2 + A
  
  private val alto4 = F.sh + G + F + E + A + A + G.sh + E
  private val alto5 = G + G + G + (D + E)~1 + F + E + A + G.sh
  private val alto6 = A + (A + G)~1 + F.sh + E + (D + G + G + E)~2 + F.sh + D
  private val alto7 = E + (D + E + F.sh + F)~2 + E + A~2 + G.sh + E
  
  private val tenor4 = A.< + D + D + (D + B.<)~1 + A + F + (E + D)~1 + C.sh
  private val tenor5 = B.< + C + (G.< + A.<)~1 + B.< + (A.< + B.< + C + D)~2 + E + E
  private val tenor6 = E + A.< + D + (G.< + A.< + B.< + D)~2 + E + (D + C)~1 + B.<
  private val tenor7 = (G.< + A.< + B.< + C)~2 + D + (C + B.< + A.< + C.< + F + D + B.< + E.<)~4 + C.sh
  
  private val bass4 = Octave(3, A + G + D.> + (A + G + F + E)~2 + D + E + A.<)
  private val bass5 = Octave(3, G.< + (C + D + E + F)~2 + G + F + (A.< + B.< + C + D)~2 + E)
  private val bass6 = Octave(3, (A + G + F.sh + E)~2 + D + (E + F)~1 + G + C + D + G.<)
  private val bass7 = Octave(3, (E + F.sh)~1 + G + (F.sh + G.sh + A + G + F + E + D + B.<)~4 + E + A.<)
    
 
  private val piano = (0,0)
  private val violin = (0,40)
  private val horn = (0,60)
  private val clarinet = (0,71)
  
  private val part1 = soprano1 | alto1 | tenor1 | bass1
  private val part2 = soprano2 | alto2 | tenor2 | bass2
  private val part3 = soprano3 | alto3 | tenor3 | bass3
  private val part4 = soprano4 | alto4 | tenor4 | bass4
  private val part5 =
    Instrument(violin,soprano5) |
    Instrument(piano,alto5) |
    Instrument(clarinet,tenor5) |
    Instrument(horn,bass5)
  private val part6 = soprano6 | alto6 | tenor6 | bass6
  private val part7 = soprano7 | alto7 | tenor7 | bass7
  
  private val director = new Director(Instrument(piano,  Tempo(60, Volume(30, part1))) + Rest(.5) +
                                      Instrument(clarinet, Tempo(75, Volume(55, part2))) + Rest(.5) +
                                      Instrument(violin, Tempo(90, Volume(80, part3))) + Rest(.5) +
                                      Instrument(horn, Tempo(105, Volume(105,part4))))
  
  /*
  private val director = new Director(Instrument(piano,  Tempo(60, Volume(30, part1))) + Rest(.5) +
                                      Instrument(clarinet, Tempo(70, Volume(50, part2))) + Rest(.5) +
                                      Instrument(violin, Tempo(80, Volume(70, part3))) + Rest(.5) +
                                      Instrument(horn, Tempo(90, Volume(90,part4))))
    */
  
  director.start()
}