package edu.depauw.scales.graphics

import java.awt.{Graphics2D, Paint}
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

/**
 * Wrapper for core `Graphics2D` rendering methods.
 */
class GraphicsContext(val g2d: Graphics2D) {
  
  // default paint for stroke
  var outlinePaint: Paint = Colors.BLACK
  
  // default paint for fill
  var fillPaint: Paint = Colors.CLEAR
  
  /**
   * renders a shape
   */
  def drawShape(shape: java.awt.Shape) {
    g2d.setPaint(fillPaint)
    g2d.fill(shape)
    g2d.setPaint(outlinePaint)
    g2d.draw(shape)
  }
  
  /**
   * renders an image
   */
  def drawImage(img: BufferedImage, x: Double, y: Double, w: Double, h: Double) {
    val transform = AffineTransform.getTranslateInstance(x, y)
    transform.scale(w / img.getWidth, h / img.getHeight)
    g2d.drawImage(img, transform, null)
  }
  
  /**
   * renders a string
   */
  def drawString(str: String, x: Double, y: Double) {
    g2d.setPaint(outlinePaint)
    g2d.drawString(str, x.toFloat, y.toFloat)
  }
}
