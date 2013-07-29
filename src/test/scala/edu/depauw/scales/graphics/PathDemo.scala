package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object PathDemo extends ScalesApp {
  
  // a fibonacci spiral
  val spiral = Path((60.0,100.0).curveTo(50,90).heading(math.Pi).
      curveTo(60,80).heading(math.Pi/2).curveTo(80,100).heading(0).curveTo(50,130).heading(3*math.Pi/2).
      curveTo(0,80).heading(math.Pi).curveTo(80,0).heading(math.Pi/2).curveTo(210,130).heading(0).
      lineTo(210,130).heading(3*math.Pi/2))
  
  // the corresponding fibonacci rectangle
  val fibRect = (Square(80)-^(Square(50)|||(((Square(10)-^Square(10)|||Square(20)))-^Square(30))))||| Square(130)
  
  // composite them on a panel
  val panel = GraphicPanel()
  panel.graphic =  Scale(3,3,spiral-&fibRect)
  
  // add panel to window
  addPanel(panel)
}
