package edu.depauw.scales.graphics

import java.awt.{Font => jFont}

/**
 * enum for font styles
 */
object FontStyleType extends Enumeration {
  type FontStyleType = Int
  val PLAIN  = java.awt.Font.PLAIN
  val BOLD   = java.awt.Font.BOLD
  val ITALIC = java.awt.Font.ITALIC
}

import FontStyleType._

/**
 * Wrapper for java.awt.Font which includes defaults
 */
case class Font(name: String = "Helvetica", style: FontStyleType = PLAIN, size: Int = 12) {
  
  /**
   * utility method to convert to a java.awt.Font
   */
  def toJavaFont: jFont = new jFont(name, style, size)
}

/**
 * Constructor for a font by name only
 */
object FontName {
  def apply(fontName: String) = new Font(fontName)
}

/**
 * Constructor for a font by style only
 */
object FontStyle {
  def apply(fontStyle: FontStyleType) = new Font(style = fontStyle)
}

/**
 * Constructor for a font by size only
 */
object FontSize {
  def apply(fontSize: Int) = new Font(size = fontSize)
}
