package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}

case class Composite(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.reverse foreach { _.render(gc) }
  }
  
  def bounds = children.foldLeft(new jRect.Double(): jRect)(
    (totalRect, g) => totalRect.createUnion(g.bounds)
  )
}