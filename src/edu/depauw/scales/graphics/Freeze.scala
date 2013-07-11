package edu.depauw.scales.graphics

import java.awt.Color
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.awt.geom.{Rectangle2D => jRect}


case class Freeze(g: Graphic) extends Graphic {
  val x =g.bounds.getX()
  val y = g.bounds.getY()
  val w = g.bounds.getWidth()
  val h = g.bounds.getHeight()
  val gc = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration
  
  val img : BufferedImage = gc.createCompatibleImage( w.toInt ,h.toInt, Transparency.TRANSLUCENT)
  g.render(new GraphicsContext(img.getGraphics.asInstanceOf[Graphics2D]))

 def render(gc : GraphicsContext) { 
   
    gc.drawImage(img,x, y, w, h)
 }
//   g2.render(gc) }
  
  def bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(n: String) = {
    Nil
  }
  

}