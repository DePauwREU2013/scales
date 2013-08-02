package edu.depauw.scales
package graphics

import edu.depauw.scales.ScalesApp

object PathDemo extends ScalesApp {
  import Util._
  
  // a fibonacci spiral
  val spiral = Path((60.0,100.0).curveTo(50,90).heading(180 deg).
      curveTo(60,80).heading(90 deg).curveTo(80,100).heading(0 deg).curveTo(50,130).heading(270 deg).
      curveTo(0,80).heading(180 deg).curveTo(80,0).heading(90 deg).curveTo(210,130).heading(0 deg).
      lineTo(210,130).heading(270 deg))
  
  // the corresponding fibonacci rectangle
  val fibRect = (Square(80)-^(Square(50)|||(((Square(10)-^Square(10)|||Square(20)))-^Square(30))))||| Square(130)
  
  // composite them on a panel
  val panel = GraphicPanel(Scale(3,3,spiral-&fibRect))
  
  // add panel to window
  addPanel(panel)
}
