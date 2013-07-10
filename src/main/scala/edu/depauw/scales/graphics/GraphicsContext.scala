package edu.depauw.scales.graphics

import java.awt.Graphics2D
import java.awt.Paint
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

class GraphicsContext(val g2d : Graphics2D) {
  var outlinePaint : Paint = Colors.BLACK
  var fillPaint : Paint = Colors.CLEAR
  
  def drawShape(shape : java.awt.Shape) {
    g2d.setPaint(fillPaint)
    g2d.fill(shape)
    g2d.setPaint(outlinePaint)
    g2d.draw(shape)
  }
  
  def drawImage(img : BufferedImage, x : Double, y : Double, w : Double, h : Double) {
    val transform = AffineTransform.getTranslateInstance(x, y)
    transform.scale(w / img.getWidth, h / img.getHeight)
    g2d.drawImage(img, transform, null)
  }
  
  def drawString(str : String, x : Double, y : Double) {
    g2d.setPaint(outlinePaint)
    g2d.drawString(str, x.toFloat, y.toFloat)
  }
}
