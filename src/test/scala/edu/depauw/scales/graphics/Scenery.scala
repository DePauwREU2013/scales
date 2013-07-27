package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object Scenery extends ScalesApp(600, 600, RenderMode.SCALE_TO_FIT, "Scenery") {
  
  // a sun
  val sun = Fill(Colors.YELLOW, Circle(15))
  
  // a single mountain
  val mountain = Fill(Colors.LIGHT_GRAY, Polygon((0,50),(25,50),(12.5,0)))
  
  // a range of mountains
  val range = mountain ||| mountain ||| mountain ||| mountain
  
  // some water
  val water = Fill(Colors.BLUE, Rectangle(100,20))
  
  // panel to draw to
  val panel = GraphicPanel()
  
  // add elements to the panel
  panel.graphic = sun -^ range -^ water
  
  // add panel to the ScalesApp window
  addPanel(panel)
}
