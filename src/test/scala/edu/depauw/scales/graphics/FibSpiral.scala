package edu.depauw.scales
package graphics

import edu.depauw.scales.ScalesApp

object FibSpiral extends ScalesApp(640, 480, RenderMode.FIT_MAX, "Fibonacci Spiral") {
  import Util._
  
  def fibRect(num: Int): Graphic = num match {
    case 0 => Square(1)
    case _ =>
      val r = fibRect(num - 1) -% (-90 deg)
      val h = r.height
      r.topLeft ||| (Square(h) -& Path((0, 0) -? (h, h) -% (270 deg)))
  }

  val panel = GraphicPanel(fibRect(35))

  // add panel to window
  addPanel(panel)
}
