package edu.depauw.scales.graphics

import java.awt.{Color, GraphicsConfiguration, GraphicsEnvironment, Transparency}
import java.awt.geom.{Rectangle2D => jRect}
import java.awt.image.BufferedImage

/** Class defining the data members of Bitmap and its member functions
 * 
 * @param fn is function which maps the square between (0,0) and (1,1) to Color values
 * @param w the width of the graphic
 * @param h the height of the graphic
 * @param x (optional) the upper left x coordinate of the graphic
 * @param y (optional) the upper left y coordinate of the graphic
 */
case class Bitmap(fn: (Double, Double) => Color,
                  w: Double, h: Double, x: Double = 0, y: Double = 0) extends Graphic {
  
  val gc: GraphicsConfiguration = 
    GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration
  
  // create an image of the given dimensions
  var img: BufferedImage = gc.createCompatibleImage(w.toInt, h.toInt, Transparency.TRANSLUCENT)
  
  // use `fn` to fill in the color values for each pixel
  for {
	i <- 0 until w.toInt
	j <- 0 until h.toInt
  } img.setRGB(i, j, fn(i / w, j / h).getRGB)
  
  def render(gc : GraphicsContext) {
    gc.drawImage(img, x, y, w, h)
  }
  
  /** Used to get the bounds of the bitmap
   * 
   * @return the values of the bounds of the bitmap.
   * 
   */
  override lazy val bounds = new jRect.Double(x, y, w, h)

  def withName(name: String) = Nil
}
