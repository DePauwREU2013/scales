package edu.depauw.scales
package graphics

import java.awt.Color

object ClippedImageTest extends ScalesApp(800, 600, RenderMode.PERCENT, "Clipping Test") {
  import Util._
  
  // path to file
  val filename = System.getProperty("user.dir") + "/resources/warningsign.jpg"
  
  // image, rotated and scaled
  val image: Graphic = Image(filename, 50, 50) -* (1.5,0.75) -% (0.3 rad)
  
  // create a bitmap
  val bitmap: Graphic = Bitmap(fn, 100, 100)
  
  // method to assign color to each pixel
  def fn(x: Double, y: Double): Color = RGBA(x, Math.min(x, y), y, 0.3)
  
  // panel to render on
  val panel = GraphicPanel(
      // clip image with Text
      (Clip(Circle(20).center, image.center) -+ (50,20)) -&
      
      // clip bitmap with Text
  	  (Clip(Text("Hello World", FontSize(18)).center, bitmap.center) -+ (50,50)) -&
  	  
  	  // clip image with composite graphic
  	  (Clip((Circle(10) ||| Square(20)).center, image.center) -+ (50,80)) 
    )
  
  // add panel to ScalesApp window
  addPanel(panel)
}
