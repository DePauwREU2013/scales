package edu.depauw.scales.graphics

import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.awt.geom.{Rectangle2D => jRect}

// (x,y) is the upper left corner, (w,h) is the size

/** Class defining the data members of Bitmap and its member functions
 * 
 * @author RudraVishweshwar
 * @param fn is function which takes two doube values and returns a color
 * @param x the upper left x coordinate of the graphic
 * @param y the upper left y coordinate of the graphic
 * @param w the width of the graphic
 * @param h the height of the graphic
 */
case class Bitmap(fn : (Double, Double) => Color,
                  x : Double, y : Double, w : Double, h : Double) extends Graphic {
  val gc = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration
  
  val img : BufferedImage = gc.createCompatibleImage(100, 100, Transparency.TRANSLUCENT)

  for (i <- 0 until 100; j <- 0 until 100) {
    val c = fn(i / 100.0, j / 100.0)
    img.setRGB(i, j, c.getRGB)
  }
  
  

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
