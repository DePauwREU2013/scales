package edu.depauw.scales
package graphics

import java.awt.Color

object ImageTest extends ScalesApp(800, 600, RenderMode.SCALE_TO_FIT, "Image Test") {
  import Util._
  
  // path to file
  val filename = System.getProperty("user.dir") + "/resources/warningsign.jpg"
  
  // image, rotated and scaled
  val image: Graphic = Image(filename, 50, 50) -* (1.5,0.75) -% (0.3 rad) centerAt (50,50)
  
  // create a bitmap
  val bitmap: Graphic = Bitmap(fn, 100, 100)
  
  // method to assign color to each pixel
  def fn(x: Double, y: Double): Color = RGBA(x, Math.min(x, y), y, 0.3)
  
  // panel to render on
  val panel = GraphicPanel(bitmap -& image)
  
  // add panel to ScalesApp window
  addPanel(panel)
}
