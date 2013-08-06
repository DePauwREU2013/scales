package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

/**
 * This test compares scaling up versus scaling down.
 */
object TestScaling extends ScalesApp {
  
  // create a pair of distinct circles
  val bigBlue = Circle(300) -* Colors.Blue -~ 2
  val tinyRed = Circle(  4) -* Colors.Red -~ 2
  
  // create a panel to draw on, with the large scaled down and the small scaled up
  val panel = GraphicPanel((bigBlue -* 0.1) -& (tinyRed -* 50))
  
  // add panel to ScalesApp window
  addPanel(panel)
}