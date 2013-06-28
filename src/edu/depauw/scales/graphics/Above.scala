package edu.depauw.scales.graphics

case class Above(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.reverse.foldRight(0: Double)((g, lastY) => {
      Translate(0, lastY, g).render(gc)
      g match {
        case Shape(s) => s.getBounds().height.toDouble + lastY
        case _ => lastY
      }
    })
  }
  
  override def ^(g : Graphic) : Graphic = g match {
    case above : Above => Above(children ++ above.children : _*)
    case _ => Above(children ++ List(g) : _*)
  }
}
