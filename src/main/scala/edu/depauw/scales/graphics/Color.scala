package edu.depauw.scales.graphics

import java.awt.Color

/**
 * Object containing common colors
 */
object Colors {
  
  // Monochrome
  val BLACK      = Color.BLACK
  val DARK_GRAY  = Color.DARK_GRAY
  val GRAY       = Color.GRAY
  val LIGHT_GRAY = Color.LIGHT_GRAY
  val WHITE      = Color.WHITE
  
  // Colored
  val RED     = Color.RED
  val PINK    = Color.PINK
  val ORANGE  = Color.ORANGE
  val YELLOW  = Color.YELLOW
  val GREEN   = Color.GREEN
  val CYAN    = Color.CYAN
  val BLUE    = Color.BLUE
  val MAGENTA = Color.MAGENTA
  
  // Colorless
  val CLEAR = RGBA(0, 0, 0, 0)
}

object RGB {
  
  /**
 * @param r red value in the range (0-255)
 * @param g green value in the range (0-255)
 * @param b blue value in the range (0-255)
 * @return corresponding java.awt.Color
 */
  def apply(r: Int, g: Int, b: Int): Color =
    new Color(r, g, b)
  
  /**
 * @param r red value in the range (0.0-1.0)
 * @param g green value in the range (0.0-1.0)
 * @param b blue value in the range (0.0-1.0)
 * @return corresponding java.awt.Color
 */
  def apply(r: Double, g: Double, b: Double): Color =
    new Color(r.toFloat, g.toFloat, b.toFloat)
}

object RGBA {
  
  /**
 * @param r red value in the range (0-255)
 * @param g green value in the range (0-255)
 * @param b blue value in the range (0-255)
 * @param alpha alpha value in the range (0-255), 0 being transparent, 255 being opaque
 * @return corresponding java.awt.Color
 */
  def apply(r: Int, g: Int, b: Int, alpha: Int): Color =
    new Color(r, g, b, alpha)

  /**
 * @param r red value in the range (0.0-1.0)
 * @param g green value in the range (0.0-1.0)
 * @param b blue value in the range (0.0-1.0)
 * @param alpha alpha value in the range (0.0-1.0), 0 being transparent, 1 being opaque
 * @return corresponding java.awt.Color
 */
  def apply(r: Double, g: Double, b: Double, alpha: Double): Color =
    new Color(r.toFloat, g.toFloat, b.toFloat, alpha.toFloat)
}

object HSV {
  
  /**
 * @param hue this component can be any floating-point number. The floor of this number is subtracted from it to create a fraction between 0 and 1. This fractional number is then multiplied by 360 to produce the hue angle in the HSB color model.
 * @param saturation in range (0.0-1.0)
 * @param value in range (0.0-1.0)
 * @return corresponding java.awt.Color
 */
  def apply(hue: Double, saturation: Double, value: Double): Color =
    Color.getHSBColor(hue.toFloat, saturation.toFloat, value.toFloat)
}

object HSVA {
   
  /**
 * @param hue this component can be any floating-point number. The floor of this number is subtracted from it to create a fraction between 0 and 1. This fractional number is then multiplied by 360 to produce the hue angle in the HSB color model.
 * @param saturation in range (0.0-1.0)
 * @param value in range (0.0-1.0)
 * @param alpha alpha value in the range (0.0-1.0), 0 being transparent, 1 being opaque
 * @return corresponding java.awt.Color
 */
  def apply(hue: Double, saturation: Double, value: Double, alpha: Double): Color = {
    val base = HSV(hue, saturation, value)
    new Color(base.getRed, base.getGreen, base.getBlue, (alpha * 255).toInt)
  }
}