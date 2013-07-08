package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}

case class Smashed(g: Graphic,w:Int,h:Int,x: Int,y:Int) extends Graphic {
  def render(gc : GraphicsContext) {
    g.render(gc)
  }
  
  def bounds: jRect = new jRect(x,y,w,h)
}

/*
 * Modifies the bounding box of the given graphic.
 * Allows changes in width, height, and the position of the bounding box
 */
object BoundsChanger {
  def apply(g: Graphic, newWidth:Int = 0, newHeight:Int = 0,
      shiftX:Int = 0, shiftY:Int = 0) = Smashed(g,newWidth,newHeight,g.bounds.x + shiftX, g.bounds.y + shiftY)
}

