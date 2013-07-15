package edu.depauw.scales.graphics

import java.awt.{Shape => jShape}
import java.awt.geom.Area

case class Clip(clip: Graphic, g: Graphic) extends Graphic {
  
  def render(gc : GraphicsContext) {
    val oldClip = gc.g2d.getClip()
    gc.g2d.setClip(clip.shape)
    g.render(gc)
    gc.g2d.setClip(oldClip)
  }
  
  override lazy val bounds = g.bounds.createIntersection(clip.bounds)
  
  override lazy val shape = {
    var area = new Area(g.shape)
    area.intersect(new Area(clip.shape))
    area
  }
  
  def withName(name: String) = g.withName(name)
}
