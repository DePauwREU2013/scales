package edu.depauw.scales.graphics

case class Smashed(g: Graphic,w:Int,h:Int) extends Graphic {
  def render(gc : GraphicsContext) {
    g.render(gc)
  }
  
  def bounds: java.awt.Rectangle = new java.awt.Rectangle(w,h)
}
object Smasher {
  def apply(g: Graphic) = Smashed(g,0,0)
}
object VSmasher {
  def apply(g: Graphic) = Smashed(g,0,g.bounds.height)
}

object HSmasher {
  def apply(g: Graphic) = Smashed(g,g.bounds.width,0)
}
