package edu.depauw.scales.music

import edu.depauw.scales._

/*
 * Liebestraum No.3 in A flat Major composed by Franz Liszt
 */

object Liebestraum extends App {

  val rightm1 = Rest(1) + C.en + E.ft.en + A.ft.en + C.>.en + A.ft.en + E.ft.en + 
  Rest(0.5) + E.ft.en + A.ft.en + C.>.en + A.ft.en + E.ft.en
  
  val leftm1 = E.<.ft + A.<<.ft + Rest(2) + C~3
  
  val rightm2 = C.en + E.en + B.ft.en + C.>.en + B.ft.en + E.en + 
  C.en + E.en + B.ft.en + C.>.en + B.ft.en + E.en
  
  val leftm2 = G.<< + Rest(4) + C
  
  val rightm3 = C.en + E.ft.en + A.en + C.>.en + A.en + E.ft.en + Rest(0.5) +
  E.ft.en + A.en + C.>.en + A.en + E.ft.en
  
  val leftm3 = F.<< + Rest(2) + C + D.ft.hn + C
  
  val rightm4 = C.en + D.en + A.ft.en + C.>.en + A.ft.en + D.en + Rest(0.5) +
  D.en + A.ft.en + C.>.en + A.ft.en + D.en
  
  val leftm4 = B.ft.<<< + Rest(2) + F.< + Rest(1) + F.<
  
  val rightm5 = Rest(0.5) + D.ft.en + E.ft.en+ C.>.en + E.ft.en + D.ft.en +
  Rest(0.5) + D.ft.en + E.ft.en + G.en + E.ft.en + D.ft.en
  
  val leftm5 = (E.ft.<<< | F.<) + G.< + A.ft.< + (E.ft.<.hn | C.hn) + B.ft.<
  
  val rightm6 = Rest(0.5) + C.en + E.ft.en + A.ft.en + E.ft.en + C.en +
   Rest(0.5) + A.ft.<.en + C.en + A.ft.en + E.ft.en + A.ft.<.en
  
  val leftm6 = (A.ft.< | A.ft.<<)~3 + A.ft.<<< + Rest(1) + E.ft.<
  
  val rightm7 = Rest(0.5) + E.ft.en + A.ft.en + C.>.en + A.ft.en + E.ft.en +
  C.en + E.ft.en + A.ft.en + C.>.en + A.ft.en + E.ft.en
  
  val leftm7 = (A.ft.<<.hn | C.hn)
  
  val rightm8 = C.en + E.en + B.ft.en + C.>.en + B.ft.en + E.en + C.en +
  E.en + B.ft.en + C.>.en + B.ft.en + E.en
  
  val leftm8 = G.<< + Rest(2) + G.ft.<< + Rest(1) + C
  
  val rightm9 = C.en + E.ft.en + A.en + C.>.en + A.en + E.ft.en +
  Rest(0.5) + E.ft.en + F.en + C.>.en + A.en + E.ft.en
  
  val leftm9 = F.<< + Rest(1) + C + (F.< | A.< | D.ft) + Rest(1) + C
  
  val rightm10 = Rest(0.5) + D.en + A.ft.en + F.>.en + C.>.en + A.ft.en +
  Rest(0.5) + A.ft.<.en + B.ft.<.en + F.en + D.ft.en + B.ft.<.en
  
  val leftm10 = (B.ft.<< | F) + Rest(2) + B.ft.<<< + Rest(1) + F.<
  
  val rightm11 = Rest(0.5) + B.ft.<.en + D.ft.en + G.en + E.ft.en +
  Rest(0.5) + D.ft.en + E.ft.en + G.en + E.ft.en + D.ft.en
  
  val leftm11 = (E.ft.<< | F.<) + G.< + A.ft.< + (E.ft.< | C) + Rest(1) +
  B.ft.<
  
  val rightm12 = Rest(0.5) + C.en + E.ft.en + A.ft.en + E.ft.en + C.en +
  Rest(0.5) + C.en + A.ft.en + E.ft.en + C.en + A.ft.<.en
  
  val leftm12 = (A.ft.< | A.ft.<<) + Rest(2) + A.ft.<<< + Rest(2)
  
  val rightm13 = Rest(0.5) + D.ft.en + A.ft.en + F.ft.en + D.ft.en +
  Rest(0.5) + A.ft.<.en + D.ft.en + A.ft.en + F.ft.en + D.ft.en + B.ft.<.en
  
  val leftm13 = (F.ft.<< | A.ft.<) + Rest(2) + (D.ft.<< | F.ft.<) + Rest(2)
  
  val rightm14 = C.en + E.ft.en + C.>.en + A.ft.en + E.ft.en + C.en +
  Rest(0.5) + E.ft.en + C.>.en + A.ft.en + E.ft.en + C.en
  
  val leftm14 = (A.ft.<< | C.<) + Rest(2) + A.ft.<<< + Rest(2)
  
  val rightm15 = Rest(0.5) + F.en + C.>.en + A.ft.en + F.en +
  Rest(0.5) + C.en + F.en + C.>.en + A.ft.en + F.en + D.en
  
  val leftm15 = (A.ft.<<.hn | C.hn) + Rest(0.5) + B.<.en + (F.<< | A.ft.<) +
  Rest(2)
  
  val rightm16 = E.en + G.en + C.>.en + E.>.en + C.>.en + G.en +
  E.en + G.en + C.>.en + E.>.en + C.>.en + G.en
  
  val leftm16 = (C.<< | G.<) + Rest(5)
  
  val rightm17 = E.en + A.en + C.>.en + E.>.en + C.>.en + A.en +
  E.en + A.en + C.>.en + E.>.en + C.>.en + A.en
  
  val leftm17 = (A.<< | E.< | C) + Rest(5)
  
  val rightm18 = E.en + G.sh.en + B.en + E.>.en + B.en + G.sh.en +
  B.<.en + E.en + G.sh.en + B.en + G.sh.en + E.en
  
  val leftm18 = (E.<< | B.<< | G.sh.<) + Rest(5)
  
  val rightm19 = C.sh.en + E.en + G.sh.en + C.sh.>.en + G.sh.en + E.en +
  Rest(0.5) + C.sh.en + E.en + G.sh.en + (G.sh.<.en | E.en) + C.en
  
  val leftm19 = (E.<< | G.sh.<) + Rest(5)
  
  val m1 = rightm1 | leftm1
  val m2 = rightm2 | leftm2
  val m3 = rightm3 | leftm3
  val m4 = rightm4 | leftm4
  val m5 = rightm5 | leftm5
  val m6 = rightm6 | leftm6
  val m7 = rightm7 | leftm7
  val m8 = rightm8 | leftm8
  val m9 = rightm9 | leftm9
  val m10 = rightm10 | leftm10
  val m11 = rightm11 | leftm11
  val m12 = rightm12 | leftm12
  val m13 = rightm13 | leftm13
  val m14 = rightm14 | leftm14
  val m15 = rightm15 | leftm15
  val m16 = rightm16 | leftm16
  val m17 = rightm17 | leftm17
  val m18 = rightm18 | leftm18
  val m19 = rightm19 |leftm19
  
  val part1 = m1+m2+m3+m4+m5+m6+m7+m8+m9+m10
  val part2 = m11+m12+m13+m14+m15+m16+m17+m18+m19
  
  val piece = Pedal(Tempo(120, part1 + part2))
  val piano = Instrument((0,2), Octave(5, piece))
  
  (new Director(piano).start)
}