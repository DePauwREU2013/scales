package edu.depauw.scales.graphics

case class Beside(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.foldLeft(0: Double)((lastX, g) => {
      Translate(lastX, 0, g).render(gc)
      g match {
        case Shape(s) => s.getBounds().width.toDouble + lastX
        case _ => lastX
      }
    })
  }
  
  def bounds = {
    children.foldLeft(new java.awt.Rectangle())(
      (totalRect, g) => {
        var translatedBounds = g.bounds.getBounds()
        translatedBounds.translate(totalRect.getWidth().toInt, 0)
        totalRect.union(translatedBounds)
      })
  }
  
  override def |||(g : Graphic) : Graphic = g match {
    case beside : Beside => Beside(children ++ beside.children : _*)
    case _ => Beside(children ++ List(g) : _*)
  }
}
