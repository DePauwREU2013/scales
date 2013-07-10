package edu.depauw.scales.graphics

import java.awt.Paint

case class Fill(paint : Paint, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldPaint = gc.fillPaint
    gc.fillPaint = paint
    g.render(gc)
    gc.fillPaint = oldPaint
  }
  
  def bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(name: String) = g.withName(name)
}
