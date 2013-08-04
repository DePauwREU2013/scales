package edu.depauw.scales
package graphics

import edu.depauw.scales.ScalesApp

object FibSpiral extends ScalesApp(640, 480, RenderMode.FIT_MAX, "Fibonacci Spiral") {
  import Util._

  def fib(num: Int): Int = num match {
    case 1 => 1
    case 2 => 1
    case _ => fib(num - 1) + fib(num - 2)

  }

  def fibRect(num: Int): Graphic = num match {
    case 1 =>
      val rect = Square(1)
      rect -& Path((rect.tl._1, rect.tl._2) -% (270 deg)
                -? (rect.br._1, rect.br._2) -% (0 deg))
                
    case _ => (num % 4) match {
      case 2 =>
        val rect = Square(fib(num))
        rect -& Path((rect.tr._1, rect.tr._2) -% (180 deg)
                  -? (rect.bl._1, rect.bl._2) -% (270 deg)) -^
          fibRect(num - 1)
          
      case 3 =>
        val rect = Square(fib(num))
        fibRect(num - 1) |||
          (rect -& Path((rect.br._1, rect.br._2) -% (90 deg)
                     -? (rect.tl._1, rect.tl._2) -% (180 deg)))
      
      case 0 =>
        val rect = Square(fib(num))
        fibRect(num - 1) -^
          (rect -& Path((rect.bl._1, rect.bl._2) -% (0 deg)
                     -? (rect.tr._1, rect.tr._2) -% (90 deg)))
      
      case 1 =>
        val rect = Square(fib(num))
        (rect -& Path((rect.tl._1, rect.tl._2) -% (270 deg)
                   -? (rect.br._1, rect.br._2) -% (0 deg))) |||
          fibRect(num - 1)
          
    }
  }

  val panel = GraphicPanel(fibRect(35))

  // add panel to window
  addPanel(panel)
}
