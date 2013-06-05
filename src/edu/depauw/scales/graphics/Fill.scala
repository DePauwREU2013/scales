package edu.depauw.scales.graphics

import java.awt.Paint

case class Fill(paint : Paint, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldPaint = gc.fillPaint
    gc.fillPaint = paint
    g.render(gc)
    gc.fillPaint = oldPaint
  }
}
