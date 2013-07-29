package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

import java.awt.Color

object TestGraphics extends ScalesApp(600, 600, RenderMode.SCALE_TO_FIT, "Graphics Test") {
  
  // create a diagonal line
  val diagLine = Line(25, 25, 75, 75)
  
  // create a red-filled polygon
  val redPoly = Polygon((10, 10), (22, 80), (25, 25), (22, 10)) -* Colors.RED
  
  // create a font
  val serifFont = new Font("Serif", FontStyleType.BOLD | FontStyleType.ITALIC)
  
  // create "Hello World" text using font
  val helloText = Text("Hello World!", serifFont) -@ (10,2)
  
  // create a function that maps (x,y) coordinates to a unique color
  def fun(x: Double, y: Double): Color = RGBA(x*y, (1-x)*y, x*(1-y), (x+1)*(y+1)/4)
  
  // use the function to create a bitmap
  val colorBitmap = Bitmap(fun, 80, 80, 10, 10)
  
  // create a panel to display graphics on
  val panel = GraphicPanel()
  
  // add a composite of all the graphics to the panel
  panel.graphic = diagLine -& redPoly -& helloText -& colorBitmap
  
  // add the panel to the ScalesApp window
  addPanel(panel)
}
