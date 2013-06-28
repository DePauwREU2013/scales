package edu.depauw.scales.graphics

case class Over(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    for (g <- children.reverse) {
      g.render(gc)
    }
  }
  
  override def |(g : Graphic) : Graphic = g match {
    case over : Over => Over(children ++ over.children : _*)
    case _ => Over(children ++ List(g) : _*)
  }
  
  override def -&(g :Graphic) : Graphic = this | g
  
}
