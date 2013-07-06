package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Blank(box: jRect = new jRect()) extends Graphic {
  def render(gc : GraphicsContext) {
  }
  
  override def |(g : Graphic) : Graphic = g
  
  def bounds = box
}

object Phantom{
  def apply(g: Graphic): Blank = Blank(new jRect(g.bounds.width,g.bounds.height))
}
