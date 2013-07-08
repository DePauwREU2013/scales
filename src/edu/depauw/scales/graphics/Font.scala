package edu.depauw.scales.graphics

import java.awt.{Font => jFont}

object FontStyleType extends Enumeration {
  type FontStyleType = Int
  val PLAIN  = java.awt.Font.PLAIN
  val BOLD   = java.awt.Font.BOLD
  val ITALIC = java.awt.Font.ITALIC
}

import FontStyleType._

case class Font(name: String = "Helvetica", style: FontStyleType = PLAIN, size: Int = 8) {
  def toJavaFont: jFont = new jFont(name, style, size)
}

object FontName {
  def apply(fontName: String) = new Font(fontName)
}

object FontStyle {
  def apply(fontStyle : FontStyleType) = new Font(style = fontStyle)
}

object FontSize {
  def apply(fontSize : Int) = new Font(size = fontSize)
}
