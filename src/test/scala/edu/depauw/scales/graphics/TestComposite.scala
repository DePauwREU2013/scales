package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestComposite extends ScalesApp {
  
  // create some shapes
  val g1 = Circle(150) -* Colors.RED
  val g2 = Rectangle(200, 100) -* Colors.BLUE
  val g3 = Polygon((0,0), (100,100), (200,0)) -* Colors.GREEN
  val g4 = Ellipse(100, 150) -* Colors.YELLOW
  
  val panel = GraphicPanel()
  
  // composite those shapes
  panel.graphic = (g1 above g2) beside (g3 over g4)
  
  // display the result
  addPanel(panel)
}
