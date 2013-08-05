package edu.depauw.scales.graphics

import java.awt.Color

/**
 * Object containing common colors
 */
object Colors {
  // Top 48 colors from the xkcd color survey
  // (http://blog.xkcd.com/2010/05/03/color-survey-results/),
  // plus White and Clear. Note that these don't always match
  // the corresponding Java or HTML color names. Oh well.
  val White = RGB(0xff, 0xff, 0xff)
  val Purple = RGB(0x7e, 0x1e, 0x9c)
  val Green = RGB(0x15, 0xb0, 0x1a)
  val Blue = RGB(0x03, 0x43, 0xdf)
  val Pink = RGB(0xff, 0x81, 0xc0)
  val Brown = RGB(0x65, 0x37, 0x00)
  val Red = RGB(0xe5, 0x00, 0x00)
  val LightBlue = RGB(0x95, 0xd0, 0xfc)
  val Teal = RGB(0x02, 0x93, 0x86)
  val Orange = RGB(0xf9, 0x73, 0x06)
  val LightGreen = RGB(0x96, 0xf9, 0x7b)
  val Magenta = RGB(0xc2, 0x00, 0x78)
  val Yellow = RGB(0xff, 0xff, 0x14)
  val SkyBlue = RGB(0x75, 0xbb, 0xfd)
  val Grey = RGB(0x92, 0x95, 0x91)
  val LimeGreen = RGB(0x89, 0xfe, 0x05)
  val LightPurple = RGB(0xbf, 0x77, 0xf6)
  val Violet = RGB(0x9a, 0x0e, 0xea)
  val DarkGreen = RGB(0x03, 0x35, 0x00)
  val Turquoise = RGB(0x06, 0xc2, 0xac)
  val Lavender = RGB(0xc7, 0x9f, 0xef)
  val DarkBlue = RGB(0x00, 0x03, 0x5b)
  val Tan = RGB(0xd1, 0xb2, 0x6f)
  val Cyan = RGB(0x00, 0xff, 0xff)
  val Aqua = RGB(0x13, 0xea, 0xc9)
  val ForestGreen = RGB(0x06, 0x47, 0x0c)
  val Mauve = RGB(0xae, 0x71, 0x81)
  val DarkPurple = RGB(0x35, 0x06, 0x3e)
  val BrightGreen = RGB(0x01, 0xff, 0x07)
  val Maroon = RGB(0x65, 0x00, 0x21)
  val Olive = RGB(0x6e, 0x75, 0x0e)
  val Salmon = RGB(0xff, 0x79, 0x6c)
  val Beige = RGB(0xe6, 0xda, 0xa6)
  val RoyalBlue = RGB(0x05, 0x04, 0xaa)
  val NavyBlue = RGB(0x00, 0x11, 0x46)
  val Lilac = RGB(0xce, 0xa2, 0xfd)
  val Black = RGB(0x00, 0x00, 0x00)
  val HotPink = RGB(0xff, 0x02, 0x8d)
  val LightBrown = RGB(0xad, 0x81, 0x50)
  val PaleGreen = RGB(0xc7, 0xfd, 0xb5)
  val Peach = RGB(0xff, 0xb0, 0x7c)
  val OliveGreen = RGB(0x67, 0x7a, 0x04)
  val DarkPink = RGB(0xcb, 0x41, 0x6b)
  val Periwinkle = RGB(0x8e, 0x82, 0xfe)
  val SeaGreen = RGB(0x53, 0xfc, 0xa1)
  val Lime = RGB(0xaa, 0xff, 0x32)
  val Indigo = RGB(0x38, 0x02, 0x82)
  val Mustard = RGB(0xce, 0xb3, 0x01)
  val LightPink = RGB(0xff, 0xd1, 0xdf)

  // Colorless
  val Clear = RGBA(0, 0, 0, 0)
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
   * @param hue this component can be any floating-point number. The
   * fractional part of this number is then multiplied by 360 to produce
   * the hue angle in the HSV color model.
   * @param saturation in range (0.0-1.0)
   * @param value in range (0.0-1.0)
   * @return corresponding java.awt.Color
   */
  def apply(hue: Double, saturation: Double, value: Double): Color =
    Color.getHSBColor(hue.toFloat, saturation.toFloat, value.toFloat)
}

object HSVA {
  /**
   * @param hue this component can be any floating-point number. The
   * fractional part of this number is then multiplied by 360 to produce
   * the hue angle in the HSV color model.
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

// TODO add a "rich color" class with various modifiers: moreRed, lessCyan, interpolateHSV, etc.