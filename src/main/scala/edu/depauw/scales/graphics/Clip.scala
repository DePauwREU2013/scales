package edu.depauw.scales.graphics

import java.awt.{ Shape => jShape }
import java.awt.geom.Area

/**
 * Provides functionality to clip a graphic with another graphic's shape
 *
 * @param clip the graphic whose shape will be used to clip `g`
 * @param g the graphic to be clipped
 */
case class Clip(clip: Graphic, g: Graphic) extends Graphic {

  def render(gc: GraphicsContext) {

    // keep a reference to the previous clip
    val oldClip = gc.g2d.getClip()

    // use the specified one
    gc.g2d.setClip(clip.shape)

    // render the graphic in this context
    g.render(gc)

    // restore the previous state
    gc.g2d.setClip(oldClip)
  }

  /**
   * The bounds of a `Clip` is equal to the bounds of the intersection of the
   * clipping `Graphic` and the clipped `Graphic`.
   */
  override lazy val bounds = g.bounds.createIntersection(clip.bounds)

  /**
   * The shape of a `Clip` is equal to the area contained by the intersection of
   * the clipping `Graphic` with the clipped `Graphic`.
   */
  override lazy val shape = {
    val area = new Area(g.shape)
    area.intersect(new Area(clip.shape))
    area
  }

  /**
   * Returns the clipped graphic's name information.  The clipping graphic's
   * name information is not included.
   */
  def withName(name: String) = g.withName(name)
  
  def names: Set[String] = g.names
}
