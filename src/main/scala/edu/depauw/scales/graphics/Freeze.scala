package edu.depauw.scales.graphics

import java.awt.{Graphics2D, GraphicsEnvironment, Transparency}
import java.awt.image.BufferedImage
import java.awt.geom.{Rectangle2D => jRect}

/**
 * rasterizes a `Graphic` to a bitmap
 * @param g Graphic to rasterize
 */
case class Freeze(g: Graphic) extends Graphic {
  
  val x = g.bounds.getX
  val y = g.bounds.getY
  
  // one artifact of raster is integral dimensions;
  // plus one captures the stroke on the bottom and right;
  val w = g.bounds.getWidth.toInt + 1
  val h = g.bounds.getHeight.toInt + 1
  
  val gc =
    GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration
  
  // render the graphic into a buffered image
  val img: BufferedImage = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT)
  g.render(new GraphicsContext(img.getGraphics.asInstanceOf[Graphics2D]))
  
  /**
   * renders the rasterized version of the graphic
   */
  def render(gc: GraphicsContext) = gc.drawImage(img, w, h, x, y)
  
  /**
   * bounds will be an integer representation of the original graphics bounds
   */
  override lazy val bounds = new jRect.Double(x,y,w,h)
  
  /**
   * all name information is discarded
   */
  def withName(n: String) = Nil

}