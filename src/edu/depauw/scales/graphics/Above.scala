package edu.depauw.scales.graphics

case class Above(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.foldLeft(0: Double)((lastY, g) => {
      Translate(0, lastY, g).render(gc)
      g match {
        case Shape(s) => s.getBounds().height.toDouble + lastY
        case Fill(p, subG) => lastY
        case _ => lastY
      }
    })
  }
  
  def bounds = {
    children.foldLeft(new java.awt.Rectangle())(
      (totalRect, g) => {
        var translatedBounds = g.bounds.getBounds()
        translatedBounds.translate(0, totalRect.getHeight().toInt)
        totalRect.union(translatedBounds)
      })
  }
  
  override def ^(g : Graphic) : Graphic = g match {
    case above : Above => Above(children ++ above.children : _*)
    case _ => Above(children ++ List(g) : _*)
  }
}
