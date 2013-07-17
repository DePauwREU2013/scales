package edu.depauw.scales.graphics

import java.awt.{Shape => jShape}
import java.awt.geom.Area

/** Class to define the data members and member functions for Clip graphics
 * 
 * @author RudraVishweshwar
 * @param clip the shape of the clip
 * @param g the graphic to be clipped
 *
 */
case class Clip(clip: Graphic, g: Graphic) extends Graphic {
    
	/**Renders the clipped graphic
     * 
     * @param gc GraphicContext
     * @return the clipped graphic.
     */
  def render(gc : GraphicsContext) {
    val oldClip = gc.g2d.getClip()
    gc.g2d.setClip(clip.shape)
    g.render(gc)
    gc.g2d.setClip(oldClip)
  }
  

  override lazy val bounds = g.bounds.createIntersection(clip.bounds)
  
  override lazy val shape = {
    var area = new Area(g.shape)
    area.intersect(new Area(clip.shape))
    area
  }
    /**Function to name the clipped image
     * 
     * @param name String containing the name
     * @return nil
     */
  def withName(name: String) = g.withName(name)
}
