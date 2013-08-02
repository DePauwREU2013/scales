package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object Scenery extends ScalesApp(600, 600, RenderMode.SCALE_TO_FIT, "Scenery") {
  
  // a sun
  val sun = Circle(15) -~* Colors.YELLOW
  
  // a single mountain
  val mountain = Polygon((0,50),(25,50),(12.5,0)) -* Colors.LIGHT_GRAY
  
  // a range of mountains
  val range = mountain ||| mountain ||| mountain ||| mountain
  
  // some water
  val water = Rectangle(100,20) -* Colors.BLUE
  
  // panel to draw to
  val panel = GraphicPanel(sun -^ range -^ water)
  
  // add panel to the ScalesApp window
  addPanel(panel)
}
