package edu.depauw.scales.graphics

import java.awt.BasicStroke

/**
 * Wrapper to specify stroke width for a given `Graphic`
 */
case class Stroke(strokeWidth: Double, g: Graphic) extends Graphic {
  /**
   * renders graphic with specified stroke width
   */
  def render(gc: GraphicsContext) {
    // save old stroke ref
    val oldStroke = gc.g2d.getStroke
    
    // apply specified stroke
    gc.g2d.setStroke(new TransformedStroke(new BasicStroke(strokeWidth.toFloat, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER), gc.g2d.getTransform))
    
    // render graphic using specified stroke
    g.render(gc)
    
    // restore old stroke
    gc.g2d.setStroke(oldStroke)
  }
  
  /** Unchanged */
  override lazy val bounds = g.bounds
  
  /** Unchanged */
  override lazy val shape = g.shape
  
  /** Unchanged */
  def withName(name: String) = g.withName(name)
  
  def names: Set[String] = g.names
}

/**
 * An implementation of {@link Stroke} which transforms another Stroke
 * with an {@link AffineTransform} before stroking with it.
 *
 * This class is immutable as long as the underlying stroke is
 * immutable.
 * 
 * Based on http://stackoverflow.com/questions/5046088/affinetransform-without-transforming-stroke
 */
class TransformedStroke(base: java.awt.Stroke, at: java.awt.geom.AffineTransform) extends java.awt.Stroke {
  val transform = new java.awt.geom.AffineTransform(at)
  val inverse = transform.createInverse

    /**
     * Strokes the given Shape with this stroke, creating an outline.
     *
     * This outline is distorted by our AffineTransform relative to the
     * outline which would be given by the base stroke, but only in terms
     * of scaling (i.e. thickness of the lines), as translation and rotation
     * are undone after the stroking.
     */
    def createStrokedShape(s: java.awt.Shape): java.awt.Shape = {
      val sTrans = transform.createTransformedShape(s)
      val sTransStroked = base.createStrokedShape(sTrans)
      inverse.createTransformedShape(sTransStroked)
    }

}