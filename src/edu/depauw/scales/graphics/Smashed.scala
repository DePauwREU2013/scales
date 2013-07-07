package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Smashed(g: Graphic,w:Int,h:Int) extends Graphic {
  def render(gc : GraphicsContext) {
    g.render(gc)
  }
  
  def bounds: jRect = new jRect(w,h)
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
