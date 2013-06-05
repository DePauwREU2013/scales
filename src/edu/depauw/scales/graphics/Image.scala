package edu.depauw.scales.graphics

import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

// (x,y) is the upper left corner, (w,h) is the size
case class Image(path : String,
                 x : Double, y : Double, w : Double, h : Double) extends Graphic {
  val img : BufferedImage = ImageIO.read(new File(path))
  
  def render(gc : GraphicsContext) {
    gc.drawImage(img, x, y, w, h)
  }
}
