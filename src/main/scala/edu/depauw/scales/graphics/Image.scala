package edu.depauw.scales.graphics

import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.geom.{Rectangle2D => jRect}

// (x,y) is the upper left corner, (w,h) is the size
case class Image(path: String, w: Double, h: Double, x: Double = 0, y: Double = 0) extends Graphic {
  
  val img : BufferedImage = ImageIO.read(new File(path))
  
  /**
   * Draws the image.
   */
  def render(gc : GraphicsContext) {
    gc.drawImage(img, w, h, x, y)
  }
  
  /**
   * identical to image dimensions
   */  
  override lazy val bounds = new jRect.Double(x, y, w, h)
  
  def withName(name: String) = Nil
}
