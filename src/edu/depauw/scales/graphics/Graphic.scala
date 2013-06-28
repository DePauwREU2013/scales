package edu.depauw.scales.graphics

trait Graphic {
  def render(gc : GraphicsContext)
  
  def |(g : Graphic) : Graphic = g match {
    case Blank => this
    case over : Over => Over(List(this) ++ over.children : _*)
    case _ => Over(this, g)
  }
  
  def -&(g : Graphic) : Graphic = this | g
  
  def |||(g : Graphic) : Graphic = g match {
    case Blank => this
    case beside : Beside => Beside(List(this) ++ beside.children : _*)
    case _ => Beside(this, g)
  }
  
  def ^(g: Graphic): Graphic = g match {
    case Blank => this
    case above: Above => Above(List(this) ++ above.children: _*)
    case _ => Above(this,g)
  }
  
  def bounds: java.awt.Rectangle
}
