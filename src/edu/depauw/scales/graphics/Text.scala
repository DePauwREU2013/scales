package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Text(x : Double, y : Double, str : String) extends Graphic {
  def render(gc : GraphicsContext) {
    gc.drawString(str, x, y)
  }
  
  /*
   * TODO: Check if there is any way to determine the width of the string
   */
  def bounds = new jRect()
}
