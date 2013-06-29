package edu.depauw.scales.graphics

import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.awt.{Rectangle => jRect}

// (x,y) is the upper left corner, (w,h) is the size
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
  
  def bounds = new jRect(x.toInt, y.toInt, w.toInt, h.toInt)
}
