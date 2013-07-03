package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}


abstract class Composite(children : Graphic*) extends Graphic 

case class Above(children : Graphic*) extends Composite {
  def render(gc : GraphicsContext) {
    children.foldLeft(new jRect(): jRect)((lastBounds, g) => {
      val h = Translate(lastBounds.x, lastBounds.y + lastBounds.height, g)
      h.render(gc)
      h.bounds
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
case class Beside(children : Graphic*) extends Composite {
  def render(gc : GraphicsContext) {
    children.foldLeft(new jRect(): jRect)((lastBounds, g) => {
      val h = Translate(lastBounds.x + lastBounds.width, lastBounds.y, g)
      h.render(gc)
      h.bounds
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

case class Over(children : Graphic*) extends Composite {
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
  
  def bounds = {
    children.foldLeft(new jRect())(
      (totalRect, g) => totalRect.union(g.bounds))
  }
}
