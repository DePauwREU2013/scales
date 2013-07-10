package edu.depauw.scales.graphics

import java.awt.Color

object Colors {
  val BLACK = Color.BLACK
  val DARK_GRAY = Color.DARK_GRAY
  val GRAY = Color.GRAY
  val LIGHT_GRAY = Color.LIGHT_GRAY
  val WHITE = Color.WHITE
  
  val RED = Color.RED
  val PINK = Color.PINK
  val ORANGE = Color.ORANGE
  val YELLOW = Color.YELLOW
  val GREEN = Color.GREEN
  val CYAN = Color.CYAN
  val BLUE = Color.BLUE
  val MAGENTA = Color.MAGENTA
  
  val CLEAR = RGBA(0, 0, 0, 0)
}

object RGB {
  def apply(r : Int, g : Int, b : Int) : Color =
    new Color(r, g, b)
  
  def apply(r : Double, g : Double, b : Double) : Color =
    new Color(r.toFloat, g.toFloat, b.toFloat)
}

object RGBA {
  def apply(r : Int, g : Int, b : Int, alpha : Int) : Color =
    new Color(r, g, b, alpha)

  def apply(r : Double, g : Double, b : Double, alpha : Double) : Color =
    new Color(r.toFloat, g.toFloat, b.toFloat, alpha.toFloat)
}

object HSV {
  def apply(hue : Double, saturation : Double, value : Double) : Color =
    Color.getHSBColor(hue.toFloat, saturation.toFloat, value.toFloat)
}

object HSVA {
  def apply(hue : Double, saturation : Double, value : Double, alpha : Double) : Color = {
    val base = HSV(hue, saturation, value)
    new Color(base.getRed, base.getGreen, base.getBlue, (alpha * 255).toInt)
  }
}