package edu.depauw.scales.graphics

import java.awt.Paint

case class Outline(paint : Paint, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldPaint = gc.outlinePaint
    gc.outlinePaint = paint
    g.render(gc)
    gc.outlinePaint = oldPaint
  }
}
