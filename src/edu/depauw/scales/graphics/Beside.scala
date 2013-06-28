package edu.depauw.scales.graphics

case class Beside(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.reverse.foldRight(0: Double)((g, lastX) => {
      Translate(lastX, 0, g).render(gc)
      g match {
        case Shape(s) => s.getBounds().width.toDouble + lastX
        case _ => lastX
      }
    })
  }
  
  override def |||(g : Graphic) : Graphic = g match {
    case beside : Beside => Beside(children ++ beside.children : _*)
    case _ => Beside(children ++ List(g) : _*)
  }
}
