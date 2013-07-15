package edu.depauw.scales.graphics

import java.awt.{Graphics2D, GraphicsEnvironment, Transparency}
import java.awt.image.BufferedImage
import java.awt.geom.{Rectangle2D => jRect}


case class Freeze(g: Graphic) extends Graphic {
  
  val x = g.bounds.getX()
  val y = g.bounds.getY()
  
  // one artifact of raster is integral dimensions;
  // plus one captures the stroke on the bottom and right;
  val w = g.bounds.getWidth().toInt + 1
  val h = g.bounds.getHeight().toInt + 1
  
  val gc = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration
  
  val img: BufferedImage = gc.createCompatibleImage( w ,h, Transparency.TRANSLUCENT)
  g.render(new GraphicsContext(img.getGraphics.asInstanceOf[Graphics2D]))
  
  def render(gc: GraphicsContext) = gc.drawImage(img,x, y, w, h)
  
  override lazy val bounds = new jRect.Double(x,y,w,h)
  
  def withName(n: String) = Nil

}