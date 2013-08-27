package edu.depauw.scales.graphics

import javax.imageio.ImageIO
import java.io.{ File, IOException }
import java.awt.{ Color, Graphics2D, GraphicsEnvironment, GraphicsConfiguration, Transparency }
import java.awt.image.BufferedImage
import java.awt.geom.{ Rectangle2D => jRect }

/**
 * A Graphic that is stored as a raster image (array of pixels).
 * Bounds are given by a rectangle of width w and height h, with upper-left at (x, y).
 * The color of each pixel may be queried.
 */
case class Image(img: BufferedImage, w: Double, h: Double, x: Double, y: Double) extends Graphic {
  /**
   * Draws the image.
   */
  def render(gc: GraphicsContext) {
    gc.drawImage(img, w, h, x, y)
  }

  /**
   * Bounds are identical to specified image dimensions
   */
  override lazy val bounds = new jRect.Double(x, y, w, h)

  def withName(name: String) = Nil
  
  def names: Set[String] = Set()

  override def color(p: Double, q: Double): Color = {
    val pp = (img.getWidth * p).toInt max 0 min (img.getWidth - 1)
    val qq = (img.getHeight * q).toInt max 0 min (img.getHeight - 1)
    new Color(img.getRGB(pp, qq), true)
  }
  
  override def writePNG(path: String): Boolean = {
    try {
      ImageIO.write(img, "png", new File(path))
    } catch {
      case ioe: IOException => false
    }
  }
  
  override def freeze: Graphic = this
}

/**
 * Companion object with various ways to construct an image
 */
object Image {
  /**
   * Construct an Image given a path to a file that Java is able to read as an image.
   * The dimensions and (x, y) offset may be specified. If either of the dimensions
   * (width or height) is omitted or zero, then the actual dimensions from the image
   * file will be used.
   *
   * @param path The path to the image file
   * @param width The desired width of the graphic
   * @param height The desired height of the graphic
   * @param x (optional) The upper-left x coordinate of the graphic
   * @param y (optional) The upper-left y coordinate of the graphic
   * @return The constructed Image
   */
  def apply(path: String, width: Double = 0, height: Double = 0, x: Double = 0, y: Double = 0): Graphic = {
    val img = try {
      ImageIO.read(new File(path))
    } catch {
      case ioe: IOException => null
    }

    if (img == null) {
      // something went wrong; return a Blank graphic
      return Blank(new jRect.Double(x, y, width, height))
    }
    
    if (width * height == 0) {
      Image(img, img.getWidth, img.getHeight, x, y)
    } else {
      Image(img, width, height, x, y)
    }
  }

  /**
   * Construct an Image given a function on the unit square that determines the color
   * at each point.
   *
   * @param fn The function that maps the square between (0,0) and (1,1) to Color values
   * @param width The width of the resulting graphic
   * @param height The height of the graphic
   * @param x (optional) The upper-left x coordinate of the graphic
   * @param y (optional) The upper-left y coordinate of the graphic
   * @return The constructed Image
   */
  def fromFunction(fn: (Double, Double) => Color, width: Double, height: Double, x: Double = 0, y: Double = 0): Graphic = {
    val w = width.toInt
    val h = height.toInt

    val gc = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration

    // create an image of the given dimensions
    val img = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT)

    // use `fn` to fill in the color values for each pixel
    for {
      i <- 0 until w
      j <- 0 until h
    } img.setRGB(i, j, fn(i / width, j / height).getRGB)

    Image(img, width, height, x, y)
  }

  /**
   * Construct an Image by "freezing" a Graphic into a rasterized bitmap.
   *
   * @param g The Graphic to be frozen
   * @return The constructed Image
   */
  def fromGraphic(g: Graphic): Graphic = {
    val x = g.originX
    val y = g.originY

    // one artifact of raster is integral dimensions;
    // adding one captures (some of) the stroke on the bottom and right
    val w = g.width.toInt + 1
    val h = g.height.toInt + 1

    val gc = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration

    // render the graphic into a buffered image
    val img = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT)

    Translate(-x, -y, g).render(new GraphicsContext(img.getGraphics.asInstanceOf[Graphics2D]))

    Image(img, w, h, x, y)
  }
}

object Bitmap {
  /**
   * Generates an Image from a function on the unit square determining the bitmap.
   * 
   * @param fn The function that maps the square between (0,0) and (1,1) to Color values
   * @param width The width of the resulting graphic
   * @param height The height of the graphic
   * @param x (optional) The upper-left x coordinate of the graphic
   * @param y (optional) The upper-left y coordinate of the graphic
   * @return The constructed Image
   */
  def apply(fn: (Double, Double) => Color, width: Double, height: Double, x: Double = 0, y: Double = 0): Graphic =
    Image.fromFunction(fn, width, height, x, y)
}

object Freeze {
  /**
   * Rasterizes a `Graphic` to a bitmap
   *
   * @param g Graphic to rasterize
   */
  def apply(g: Graphic): Graphic = Image.fromGraphic(g)
}
