package edu.depauw.scales.graphics

import java.awt.Color

case class Freeze(g: Graphic) extends Graphic {
 def render(gc : GraphicsContext) { 
   def fn(x:Double, y:Double): Color = RGBA(g.bounds.getX, g.bounds.getY, y, 0.3)
   val g2 : Graphic = Bitmap(fn, 0,0, g.bounds.getWidth, g.bounds.getHeight)
  
   g2.render(gc) }
  
  def bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(n: String) = {
    Nil
  }
  

}