package edu.depauw.scales.graphics

import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import java.awt.font.TextLayout
import java.awt.{Font => jFont}

import FontStyleType._

case class Text(str: String,
				font: Font = new Font(),
				frc: FontRenderContext = new FontRenderContext(new AffineTransform(), true, true)
		   ) extends Graphic {
  
  val text: TextLayout = new TextLayout(str, font.toJavaFont, frc)
  
  def render(gc : GraphicsContext) {
    text.draw(gc.g2d, 0, 0)
    
    // Use this for debugging alignment with text
    // gc.drawShape(text.getBounds())
  }
  
  def bounds = text.getBounds()
  
  override lazy val shape = text.getOutline(new AffineTransform())
  
  def withName(name: String) = Nil
  
  def setFont(name: String, style: FontStyleType, size: Int): Text = {
    new Text(str, new Font(name, style, size), frc)
  }
  
  def setSize(size: Int): Text = {
    font match {
      case Font(oldName, oldStyle, oldSize) =>
        new Text(str, new Font(oldName, oldStyle, size), frc)
    }
  }
  
  def setFontFamily(name: String): Text = {
    font match {
      case Font(oldName, oldStyle, oldSize) =>
        new Text(str, new Font(name, oldStyle, oldSize), frc)
    }
  }
  
  def setStyle(style: FontStyleType): Text = {
    font match {
      case Font(oldName, oldStyle, oldSize) =>
        new Text(str, new Font(oldName, style, oldSize), frc)
    }
  }
}
