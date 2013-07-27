package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

import java.awt.Color

object ClippedImageTest extends ScalesApp(800, 600, RenderMode.SCALE_TO_FIT, "Clipping Test") {
  
  // path to file
  val filename = System.getProperty("user.dir") + "/resources/warningsign.jpg"
  
  // image, rotated and scaled
  val image: Graphic =
    Rotate(0.3, 50, 50, Scale(1.5, 0.75, 50, 50, Image(filename, 50, 50, 25, 25)))
  
  // create a bitmap
  val bitmap: Graphic = Bitmap(fn, 100, 100)
  
  // method to assign color to each pixel
  def fn(x: Double, y: Double): Color = RGBA(x, Math.min(x, y), y, 0.3)
  
  // panel to render on
  val panel = GraphicPanel()
  
  // add graphics to panel
  panel.graphic = (
      
      // clip image with Text
      Translate(50,20,Clip(Circle(20).center, image.center)) -&
      
      // clip bitmap with Text
  	  Translate(50,50,Clip(Text("Hello World", FontSize(18)).center, bitmap.center)) -&
  	  
  	  // clip image with composite graphic
  	  Translate(50,80,Clip((Circle(10) ||| Square(20)).center, image.center)) 
    )
  
  // add panel to ScalesApp window
  addPanel(panel)
}
