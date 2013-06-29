package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Above(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.foldLeft(0: Double)((lastY, g) => {
      Translate(0, lastY, g).render(gc)
      g.bounds.height + lastY
    })
  }
  
  /*
   * bounds is the union of all the childrens'
   * bounds after they are translated
   */
  def bounds = {
    children.foldLeft(new jRect())(
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
