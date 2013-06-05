package edu.depauw.scales.graphics

case class Text(x : Double, y : Double, str : String) extends Graphic {
  def render(gc : GraphicsContext) {
    gc.drawString(str, x, y)
  }
}
