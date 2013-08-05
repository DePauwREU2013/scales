package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestComposite extends ScalesApp {
  
  // create some shapes
  val g1 = Circle(150) -* Colors.Red
  val g2 = Rectangle(200, 100) -* Colors.Blue
  val g3 = Polygon((0,0), (100,100), (200,0)) -* Colors.Green
  val g4 = Ellipse(100, 150) -* Colors.Yellow
  
  val panel = GraphicPanel((g1 above g2) beside (g3 over g4))
  
  // display the result
  addPanel(panel)
}
