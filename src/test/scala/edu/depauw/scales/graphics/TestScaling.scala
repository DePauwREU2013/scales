package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

/**
 * This test compares scaling up versus scaling down.
 */
object TestScaling extends ScalesApp {
  
  // create a pair of distinct circles
  val bigBlue = Fill(Colors.BLUE, Circle(300))
  val tinyRed = Fill(Colors.RED , Circle(  4))
  
  // create a panel to draw on
  val panel = GraphicPanel()
  
  // scale the large down and the small one up
  panel.graphic = (bigBlue -* 0.1) -& (tinyRed -* 50)
  
  // add panel to ScalesApp window
  addPanel(panel)
}