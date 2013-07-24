package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}

/** Blank is a class that is used to define a graphic with only a bounding box.
 * 
 * @author RudraVishweshwar
 * @param box is a jRect which creates a new Rectangle
 *
 */
case class Blank(box: jRect = new jRect.Double()) extends Graphic {
 
  def render(gc : GraphicsContext) {
  }
  
  override def |(g : Graphic) : Graphic = g
  /** Used to get the bounds of the blank
   * 
   * @return the values of the bounds of the blank.
   * 
   */
  override lazy val bounds = box
  
  def withName(name: String) = Nil
}

object Phantom extends Blank {
  def apply(g: Graphic): Blank = 
    Blank(new jRect.Double(0, 0, g.bounds.getWidth, g.bounds.getHeight))
}
