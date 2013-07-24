package edu.depauw.scales.graphics

import java.awt.Paint

case class Outline(paint : Paint, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldPaint = gc.outlinePaint
    gc.outlinePaint = paint
    g.render(gc)
    gc.outlinePaint = oldPaint
  }
  
  /** Used to get the bounds of the outline of the graphic
   * 
   * @return the values of the bounds of the outline of the graphic.
   * 
   */  
  override lazy val bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(name: String) = g.withName(name)
}
