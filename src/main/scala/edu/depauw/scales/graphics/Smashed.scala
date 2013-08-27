package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}

/**
 * Wrapper to specify arbitrary `bounds` for a given `Graphic`
 */
case class Smashed(g: Graphic, w: Double = 0, h: Double = 0, x: Double = 0, y: Double = 0) extends Graphic {
  
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
  
  def names: Set[String] = g.names
}
