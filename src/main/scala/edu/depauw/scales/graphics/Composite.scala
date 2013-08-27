package edu.depauw.scales.graphics

import java.awt.geom.Area

case class Composite(g: Graphic, h: Graphic) extends Graphic {

  /**
   * renders each component graphic
   */
  def render(gc: GraphicsContext) {
    h.render(gc)
    g.render(gc)
  }

  /**
   * `bounds` is the union of the bounds of the components
   */
  override lazy val bounds =
    if (g.bounds.isEmpty) h.bounds // TODO is this the right way to ignore "phantom" graphics?
    else if (h.bounds.isEmpty) g.bounds
    else g.bounds.createUnion(h.bounds)

  /**
   * shape is computed as the union of the two areas of the components' shapes
   */
  override lazy val shape = {
    val area = new Area(g.shape)
    area.add(new Area(h.shape))
    area
  }

  /**
   * matched-by-name graphics from each component graphic are returned
   */
  def withName(n: String) = g.withName(n) ++ h.withName(n)
  
  def names: Set[String] = g.names ++ h.names
}