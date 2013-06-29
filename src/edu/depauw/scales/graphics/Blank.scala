package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case object Blank extends Graphic {
  def render(gc : GraphicsContext) {
  }
  
  override def |(g : Graphic) : Graphic = g
  
  def bounds = new jRect()
}
