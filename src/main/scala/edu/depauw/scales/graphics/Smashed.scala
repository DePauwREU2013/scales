package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}

/**
 * Wrapper to specify arbitrary `bounds` for a given `Graphic`
 */
case class Smashed(g: Graphic, w: Double, h: Double, x: Double, y:Double) extends Graphic {
  
  /**
   * renders the graphic
   */
  def render(gc: GraphicsContext) = g.render(gc)

  /**
   * returns the bounds specified by the constructor
   */
  override lazy val bounds = new jRect.Double(x, y, w, h)
  
  /**
   * same as original graphic
   */
  def withName(name: String) = g.withName(name)
}

/*
 * Modifies the bounding box of the given graphic.
 * Allows changes in width, height, and the position of the bounding box
 */
object BoundsChanger {
  def apply(g: Graphic, newWidth: Double = 0, newHeight: Double = 0,
      shiftX: Double = 0, shiftY: Double = 0) =
    Smashed(g, newWidth, newHeight, g.bounds.getX + shiftX, g.bounds.getY + shiftY)
}
