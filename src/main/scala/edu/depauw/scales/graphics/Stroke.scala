package edu.depauw.scales.graphics

import java.awt.BasicStroke

case class Stroke(width : Double, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldStroke = gc.g2d.getStroke
    gc.g2d.setStroke(new BasicStroke(width.toFloat, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER))
    g.render(gc)
    gc.g2d.setStroke(oldStroke)
  }
  
  /*
   * TODO: Check whether the stroke should be included
   * 	   into the bounds calculation or not.
   */
  def bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(name: String) = g.withName(name)
}
