package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object FreezeImageTest extends ScalesApp(600, 600, RenderMode.SCALE_TO_FIT, "Freeze Test") {
  
  // create a vector circle
  val vector = Circle(20)
  
  // rasterize it
  val raster = vector.freeze
  
  // create panel to compare graphics side-by-side
  val panel = GraphicPanel(vector -|| raster)
  
  // add panel to ScalesApp window
  addPanel(panel)
}
