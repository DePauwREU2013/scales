package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestComposite extends ScalesApp {
  
  // create some shapes
  val g1 = Fill(Colors.RED   , Circle(150))
  val g2 = Fill(Colors.BLUE  , Rectangle(0, 0, 200, 100))
  val g3 = Fill(Colors.GREEN , Polygon((0,0), (100,100), (200,0)))
  val g4 = Fill(Colors.YELLOW, Ellipse(0, 0, 100, 150))
  
  val panel = GraphicPanel()
  
  // composite those shapes
  panel.graphic = (g1 above g2) beside (g3 over g4)
  
  // display the result
  addPanel(panel)
}
