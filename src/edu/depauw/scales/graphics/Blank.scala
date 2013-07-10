package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}

case class Blank(box: jRect = new jRect.Double()) extends Graphic {
  def render(gc : GraphicsContext) {
  }
  
  override def |(g : Graphic) : Graphic = g
  
  def bounds = box
  
  def withName(name: String) = Nil
}

object Phantom extends Blank {
  def apply(g: Graphic): Blank = 
    Blank(new jRect.Double(0, 0, g.bounds.getWidth, g.bounds.getHeight))
}
