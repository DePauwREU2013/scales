package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Beside(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.foldLeft(0: Double)((lastX, g) => {
      Translate(lastX, 0, g).render(gc)
      g.bounds.width + lastX
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
        translatedBounds.translate(totalRect.getWidth().toInt, 0)
        totalRect.union(translatedBounds)
      })
  }
  
  override def |||(g : Graphic) : Graphic = g match {
    case beside : Beside => Beside(children ++ beside.children : _*)
    case _ => Beside(children ++ List(g) : _*)
  }
}
