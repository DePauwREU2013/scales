package edu.depauw.scales.graphics

import java.awt

case class Clip(clip: Graphic, g: Graphic) extends Graphic {
  
  /*
   * TODO: This can be improved. Currently only very shallow support.
   *       One option is to add a `shape` method to Graphic, which
   *       allows one to query the appropriate Shape (java) of any
   *       given Graphic.
   */
  val shape: java.awt.Shape = clip match {
    case s: Shape => s.shape
    case Transform(xform, s: Shape) => xform.createTransformedShape(s.shape)
    case _ => clip.bounds
  }
   
  def render(gc : GraphicsContext) {
    val oldClip = gc.g2d.getClip()
    gc.g2d.setClip(shape)
    g.render(gc)
    gc.g2d.setClip(oldClip)
  }
  
  def bounds = g.bounds.createIntersection(clip.bounds)
  
}
