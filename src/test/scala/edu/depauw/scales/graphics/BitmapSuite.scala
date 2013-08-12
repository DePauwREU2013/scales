package edu.depauw.scales.graphics

import org.scalatest.FunSuite

import java.awt.Color

class BitmapSuite extends FunSuite {
  def fun(x: Double, y: Double): Color = RGBA(x*y, (1-x)*y, x*(1-y), (x+1)*(y+1)/4)

  val b = Bitmap(fun, 80, 80, 10, 10)

  test("A Bitmap can render an arbitrary function from [0,1)x[0,1) to Color") {
    // This is a stupid test...
    val img = b.asInstanceOf[Image]
    assert(img.color(0, 0) === RGBA(0, 0, 0, 64))
    assert(img.color(0, 1) === RGBA(0, 253, 0, 127))
    assert(img.color(1, 0) === RGBA(0, 0, 253, 127))
    assert(img.color(1, 1) === RGBA(249, 3, 3, 252))
  }
  
  test("A Bitmap has the specified bounds") {
    val bounds = b.bounds
    
    assert(bounds.getMinX === 10)
    assert(bounds.getMinY === 10)
    assert(bounds.getMaxX === 90)
    assert(bounds.getMaxY === 90)
  }
}