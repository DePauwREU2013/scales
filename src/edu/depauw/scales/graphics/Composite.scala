package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Composite(children : Graphic*) extends Graphic {
  def render(gc : GraphicsContext) {
    children.reverse foreach { _.render(gc) }
  }
  
  def bounds = children.foldLeft(new jRect())(
    (totalRect, g) => totalRect union g.bounds
  )
}