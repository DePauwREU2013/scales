package edu.depauw.scales.graphics

import java.awt.BasicStroke

case class Stroke(width : Double, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldStroke = gc.g2d.getStroke
    gc.g2d.setStroke(new BasicStroke(width.toFloat, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER))
    g.render(gc)
    gc.g2d.setStroke(oldStroke)
  }
}
