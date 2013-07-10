package edu.depauw.scales.music

import edu.depauw.scales._

object Prelude {
  def measure(notes : Note*) : Step = {
    val left = notes(0) + notes(1)
    val right = notes(2) + notes(3) + notes(4)
    val half = (left + right + right)
    Pedal(half + half)~4
  }
  
  val final1 = Pedal(C.<< + C.< + F.< + A.< + C + A.< + C + F +
                     C + A.< + C + A.< + F.< + A.< + F.< + D.<)~4
  val final2 = Pedal(C.<< + B.<< + G + B + D.> + F.> + D.> + B +
                     D.> + B + G + B + D + F + E + D)~4
  val final3 = (C.<< | G.<< | C.< | C | E | G | C.>)~4
  
  val piece0 = measure(C, E, G, C.>, E.>) + measure(C, D, A, D.>, F.>) +
    measure(B.<, D, G, D.>, F.>) + measure(C, E, G, C.>, E.>)
  val piece1 = piece0
  val piece2 = measure(C, E, A, E.>, A.>) + measure(C, D, F.sh, A, D.>) +
    measure(B.<, D, G, D.>, G.>) + measure(B.<, C, E, G, C.>)
  val piece3 = measure(A.<, C, E, G, C.>) + measure(D.<, A.<, D, F.sh, C.>) +
    measure(G.<, B.<, D, G, B) + measure(G.<, B.ft.<, E, G, C.sh.>)
  val piece4 = measure(F.<, A.<, D, A, D.>) + measure(F.<, A.ft.<, D, F, B) +
    measure(E.<, G.<, C, G, C.>) + measure(E.<, F.<, A.<, C, F)
  val piece5 = measure(D.<, F.<, A.<, C, F) + measure(G.<<, D.<, G.<, B.<, F) +
    measure(C.<, E.<, G.<, C, E) + measure(C.<, G.<, B.ft.<, C, E)
  val piece6 = measure(F.<<, F.<, A.<, C, E) + measure(F.sh.<<, C.<, A.<, C, E.ft) +
    measure(G.<<, E.ft.<, B.<, C, E.ft) + measure(A.ft.<<, F.<, B.<, C, D)
  val piece7 = measure(G.<<, F.<, G.<, B.<, D) + measure(G.<<, E.<, G.<, C, E) +
    measure(G.<<, D.<, G.<, C, F) + measure(G.<<, D.<, G.<, B.<, F)
  val piece8 = measure(G.<<, E.ft.<, A.<, C, F.sh) + measure(G.<<, E.<, G.<, C, G) +
    measure(G.<<, D.<, G.<, C, F) + measure(G.<<, D.<, G.<, B.<, F)
  val piece9 = measure(C.<<, C.<, G.<, B.ft.<, E) + final1 + final2 + final3
  
  val prelude = piece0 + piece1 + piece2 + piece3 + piece4 +
    piece5 + piece6 + piece7 + piece8 + piece9
    
  def main(args : Array[String]) {
    val director = new Director(Tempo(90, prelude))
    director.start()
  }
}
