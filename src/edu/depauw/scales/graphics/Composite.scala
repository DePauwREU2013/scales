package edu.depauw.scales.graphics

import java.awt.geom.Area

case class Composite(g: Graphic, h: Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    g.render(gc)
    h.render(gc)
  }
  
  override lazy val bounds = g.bounds.createUnion(h.bounds)
  
  override lazy val shape = {
    var area = new Area(g.shape)
    area.add(new Area(h.shape))
    area
  }
    
  def withName(n: String) = g.withName(n) ++ h.withName(n)
}