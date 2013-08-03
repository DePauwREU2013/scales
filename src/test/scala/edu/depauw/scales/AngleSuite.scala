package edu.depauw.scales

import org.scalatest.FunSuite

class AngleSuite extends FunSuite {
  import Util._

  test("Angles are typesafe") {
    val a = (90 deg)
    val b = (0.25 rev)
    assert(a === b)
  }
  
  test("Angles may be added and multiplied") {
    val a = (90 deg)
    val b = a * 2
    val c = b / 3
    val d = 6 * c
    val e = d - b
    val f = c + c + c
    assert(a === (90 deg))
    assert(b === (Pi rad))
    assert(c === (60 deg))
    assert(d === (1 rev))
    assert(e === f)
  }
}