package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}

case class Composite(g: Graphic, h: Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    g.render(gc)
    h.render(gc)
  }
  
  def bounds = g.bounds.createUnion(h.bounds)
  
  override def withName(n: String) = g.withName(n) ++ h.withName(n)
}