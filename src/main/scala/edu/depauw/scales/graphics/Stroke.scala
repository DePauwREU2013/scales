package edu.depauw.scales.graphics

import java.awt.BasicStroke

/**
 * Wrapper to specify stroke width for a given `Graphic`
 */
case class Stroke(width : Double, g : Graphic) extends Graphic {
  
  /**
   * renders graphic is specified stroke width
   */
  def render(gc : GraphicsContext) {
    // save old stroke ref
    val oldStroke = gc.g2d.getStroke
    
    // apply specified stroke
    gc.g2d.setStroke(new BasicStroke(width.toFloat, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER))
    
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
}
