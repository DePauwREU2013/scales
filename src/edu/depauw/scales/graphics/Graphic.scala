package edu.depauw.scales.graphics

trait Graphic {
  def render(gc : GraphicsContext)
  
  def |(g : Graphic) : Graphic = g match {
    case Blank => this
    case over : Over => Over(List(this) ++ over.children : _*)
    case _ => Over(this, g)
  }
}
