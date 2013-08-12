package edu.depauw.scales.graphics

import java.awt.Paint

case class Fill(paint: Paint, g: Graphic) extends Graphic {
  
  /**
   * renders graphic filled with specified paint
   */
  def render(gc : GraphicsContext) {
    // keep ref of previous paint
    val oldPaint = gc.fillPaint
    
    // switch to specified one
    gc.fillPaint = paint
    
    // render using it
    g.render(gc)
    
    // restore previous one
    gc.fillPaint = oldPaint
  }
  
  /** 
   * bounds is unchanged
   */  
  def bounds = g.bounds
  
  /**
   * matched-by-name graphics from original graphic are returned
   */
  def withName(name: String) = g.withName(name)
}
