package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import java.awt.font.TextLayout
import java.awt.{Font => jFont}

case class Text(str: String,
				font: jFont = new jFont("Helvetica", jFont.PLAIN, 8),
				frc: FontRenderContext = new FontRenderContext(new AffineTransform(), true, true)
		   ) extends Graphic {
  
  val text: TextLayout = new TextLayout(str, font, frc)
  
  def render(gc : GraphicsContext) {
    text.draw(gc.g2d, 0, 0)
    
    // Use this for debugging alignment with text
    //gc.drawShape(text.getBounds())
  }
  
  def bounds: jRect = text.getBounds().getBounds()
}
