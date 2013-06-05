package edu.depauw.scales.graphics

case object Blank extends Graphic {
  def render(gc : GraphicsContext) {
  }
  
  override def |(g : Graphic) : Graphic = g
}
