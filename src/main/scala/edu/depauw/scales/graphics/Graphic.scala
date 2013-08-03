package edu.depauw.scales
package graphics

import java.awt.geom.{AffineTransform, Rectangle2D => jRect}
import java.awt.{Shape => jShape}
import java.awt.Paint

/**
 * This is the primary superclass of all primitive elements that can be displayed in Scales.
 * 
 * Each subclass that implements `Graphic` must know how to render itself to a given 
 * GraphicsContext.  It should provide a definition of a bounding box (`bounds`), as well as a 
 * definition of its shape, in the cases that the shape is different from the bounds. Finally, it
 * should also provide a means of being queried for names. Those are the only "abstract"
 * definitions of this trait (although technically `bounds` and `shape` already have defaults).
 * 
 * The majority of the remaining methods defined within this trait consist of various means of 
 * transforming a general `Graphic` to some subclass `Graphic` (e.g., `Transform` or `Composite`).  
 * These methods fall into the following general categories: 
 * 
 *  - '''transformations''' (see [[edu.depauw.scales.graphics.Transform]])
 *  - '''compositing''' (see [[edu.depauw.scales.graphics.Composite]])
 *  - '''fill and stroke''' (see [[edu.depauw.scales.graphics.Fill]])
 *  - '''alignment'''
 *  - '''bounds manipulation''' (see [[edu.depauw.scales.graphics.Smashed]])
 *  - '''rasterizing'''  (see [[edu.depauw.scales.graphics.Freeze]])
 * 
 * There is also a set of utility methods to extract common information, particularly point info.
 */
trait Graphic {
  
  /**
   * All Graphics must implement this method which handles rendering via
   * the graphics context.
   * 
   * @param gc GraphicsContext
   * @return Unit
   * 
   */
  def render(gc: GraphicsContext)

  /**
   * All Graphics should specify a bounding box
   */
  def bounds: jRect
  
  /**
   * All Graphics should specify a java.awt.Shape representation. Default is the bounding box.
   */
  def shape: jShape = bounds
  
  /** Function to query the Graphic for all Graphics with a given name
   * 
   * @param name String containing the name of the Graphic(s) to be found
   * @return List[Graphic] 
   * 
   */
  def withName(name: String): List[Graphic] // TODO does this really need to be defined on each Graphic?
  
  /* ========== Query methods ========== */
  
  /**
   * Get the width of this Graphic.
   */
  def width: Double = bounds.getWidth
  
  /**
   * Get the height of this Graphic.
   */
  def height: Double = bounds.getHeight
  
  /**
   * Get the left x-coordinate of this Graphic.
   */
  def tlX: Double = bounds.getX
  
  /**
   * Get the top y-coordinate of this Graphic.
   */
  def tlY: Double = bounds.getY
  
  /* ========== Transform Methods ========== */
  
  /**
   * Method to translate a graphic on the panel
   */
  def translate(dx: Double, dy: Double): Graphic = Translate(dx, dy, this)
  
  /**
   * Method to translate a graphic on the panel
   */
  def translate(displacement: (Double, Double)): Graphic = 
    Translate(displacement._1, displacement._2, this)
  
  /**
   * `translate` alias
   */
  def -+ (dx: Double, dy: Double): Graphic = translate(dx: Double, dy: Double)
  
  /**
   * `translate` alias
   */
  def -+ (pos: (Double,Double)): Graphic = translate(pos)
  
  /**
  * @param s amount to scale graphic
  * @return the graphic scaled
  */
  def scale(s: Double): Graphic = Scale(s, this)
  
  /**
   * @param sx horizontal scale factor
   * @param sy vertical scale factor
   * @return the graphic scaled
   */
  def scale(sx: Double, sy: Double): Graphic = Scale(sx, sy, this)
  
  /**
   * `scale` alias
   */
  def -* (s: Double): Graphic = scale(s)
  
  /**
   * `scale` alias
   */
  def -* (sx: Double, sy: Double): Graphic = scale(sx, sy)
  
  /**
   * Applies a general transformation.
   */
  def -* (transform: AffineTransform): Graphic = Transform(transform, this)
  
  /**
   * Rotate clockwise by angle theta.
   */
  def rotate(theta: Angle): Graphic = Rotate(theta, this)
  
  /**
   * `rotate` alias
   */
  def -% (theta: Angle): Graphic = rotate(theta)
  
   /** Method to move a graphic on the panel
   * 
   * @param x the x coordinate of the position to be moved to
   * @param y the y coordinate of the position to be moved to
   * @return Graphic at the new position
   * 
   */
  def moveTo(x: Double, y: Double): Graphic = {
    Translate(x - tlX, y - tlY, this)
  }
  
   /** `moveTo` alias
   * 
   * @param x the x coordinate of the position to be moved to
   * @param y the y coordinate of the position to be moved to
   * @return Graphic at the new position
   * 
   */
  def -@ (x: Double, y: Double): Graphic = moveTo(x,y)
  
  /** Method to center a graphic on a given point
   * 
   * @param x the x coordinate of the position to be centered at
   * @param y the y coordinate of the position to be centered at
   * @return centered graphic
   * 
   */
  def centerAt(x: Double, y: Double): Graphic = moveTo(x - width/2, y - height/2)
  
  /** Method to center a graphic on a given point
   * 
   * @param x the x coordinate of the position to be centered at
   * @param y the y coordinate of the position to be centered at
   * @return centered graphic
   * 
   */
  def centerAt(point: (Double,Double)): Graphic = point match { case (x,y) => centerAt(x,y) }
  
  /* ======= Fill, Stroke, Outline Methods ======= */
  
  /**
   * @param paint color to fill graphic with 
   * @return graphic using `paint` as the fill color
   */
  def fill(paint: Paint): Graphic = Fill(paint, this)
  
  /** `fill` alias
   * @param paint color to fill graphic with 
   * @return graphic using `paint` as the fill color
   */
  def -* (paint: Paint): Graphic = this fill paint
  
  /**
   * @param width thickness of stroke
   * @param paint color to use on the stroke
   * @return graphic using updated stroke thickness and color
   */
  def stroke(width: Double, paint: Paint): Graphic =
    Stroke(width, Outline(paint, this))
  
   /** `stroke(Double, Paint)` alias
   * @param width thickness of stroke
   * @param paint color to use on the stroke
   * @return graphic using updated stroke thickness and color
   */
  def -~ (width: Double, paint: Paint): Graphic = this stroke (width, paint)
  
   /**
   * @param width thickness of stroke
   * @return graphic using updated stroke thickness
   */
  def stroke(width: Double): Graphic = Stroke(width, this)
  
  /** `stroke(Double)` alias
   * @param width thickness of stroke
   * @return graphic using updated stroke thickness
   */
  def -~ (width: Double): Graphic = this stroke width
  
  /**
   * @param paint color to use on the outline stroke
   * @return graphic using updated outline color
   */
  def outline(paint: Paint): Graphic = Outline(paint, this)
  
  /** `outline` alias
   * @param paint color to use on the outline stroke
   * @return graphic using updated outline color
   */
  def -~ (paint: Paint): Graphic = this outline paint
  
  /**
   * @param paint color to use for fill and outline
   * @return graphic using updated fill and outline color
   */
  def fillAll(paint: Paint): Graphic =
    Outline(paint, Fill(paint, this))
    
  /** `fillAll` alias
   * @param paint color to use for fill and outline
   * @return graphic using updated fill and outline color
   */
  def -~* (paint: Paint): Graphic = this fillAll paint
  
  /* ========= Compositing methods ========= */
  
  /** Method to put a Graphic over another Graphic
   * 
   * @param g Graphic to be placed Over
   * @return Graphic with the new composite
   * 
   */
  def over(g : Graphic) : Graphic = Composite(this, g)
  
  /** `over` alias
   * 
   * @param g Graphic to be placed Over
   * @return Graphic with the new composite
   * 
   */
  def -&(g : Graphic) : Graphic = this over g
  
  /** `over` alias
   * 
   * @param g Graphic to be placed Over
   * @return Graphic with the new composite
   * 
   */
  def |(g : Graphic) : Graphic = this over g
  
  /** Function to put a Graphic beside another Graphic
   * 
   * @param g Graphic to be placed beside
   * @return Graphic with the new composite
   * 
   */
  def beside(g : Graphic) : Graphic = {
    Composite(this, Translate(tlX + width - g.tlX, 0, g))
  }
  
  /** `beside` alias
   * 
   * @param g Graphic to be placed beside
   * @return Graphic with the new composite
   * 
   */
  def |||(g: Graphic): Graphic = this beside g
  
   /** `beside` alias
   *   
   * @param g Graphic to be placed beside
   * @return Graphic with the new composite
   * 
   */
  def -||(g: Graphic): Graphic = this beside g
  
  /** Function to put a Graphic above another Graphic
   * 
   * @param g Graphic to be placed above
   * @return Graphic with the new composite
   * 
   */
  def above(g: Graphic): Graphic = {
    Composite(this,
      Translate(0, tlY + height - g.tlY, g))
  }
  
  /** Shortcut Function for above
   * 
   * @param g Graphic to be placed above
   * @return Graphic with the new composite
   * 
   */
    def -^(g: Graphic): Graphic = this above g
  
  /* ========= Alignment methods ========= */
  
   /** Function to move a graphic to the topleft position
   * 
   * @param nil
   * @return Graphic at the top left position
   * 
   */
  def topLeft: Graphic = moveTo(0, 0)
  
  /** Function to move a graphic to the middle left position
   * 
   * @param nil
   * @return Graphic at the midle left position
   * 
   */
  def middleLeft: Graphic = moveTo(0, -height/2)
  
  /** Function to move a graphic to the bottom left position
   * 
   * @param nil
   * @return Graphic at the top bottom left position
   * 
   */
  def bottomLeft: Graphic = moveTo(0, -height)
  
  /** Function to move a graphic to the top center position
   * 
   * @param nil
   * @return Graphic at the top center position
   * 
   */
  def topCenter: Graphic = moveTo(-width/2, 0)
  
  /** Function to move a graphic to the middle center position
   * 
   * @param nil
   * @return Graphic at the middle center position
   * 
   */
  def middleCenter: Graphic =
    moveTo(-width/2, -height/2)
  
    /** Function to move a graphic to the center position
   * 
   * @param nil
   * @return Graphic at the center position
   * 
   */
  def center: Graphic = middleCenter
  
  /** Function to move a graphic to the bottom center position
   * 
   * @param nil
   * @return Graphic at the bottom center position
   * 
   */
  def bottomCenter: Graphic =
    moveTo(-width/2, -height)
  
   /** Function to move a graphic to the top right position
   * 
   * @param nil
   * @return Graphic at the top right position
   * 
   */
  def topRight: Graphic = moveTo(-width, 0)
 
  /** Function to move a graphic to the middle right position
   * 
   * @param nil
   * @return Graphic at the middle right position
   * 
   */  
  def middleRight: Graphic =
    moveTo(-width, -height/2)
  
   /** Function to move a graphic to the bottom right position
   * 
   * @param nil
   * @return Graphic at the bottom right position
   * 
   */
  def bottomRight: Graphic = 
    moveTo(-width, -height)
    
    //======= Point returning methods ========
  
    /** Function to return the upper left coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the upper left part of the graphic
   * 
   */  
  def tl: (Double,Double) = (tlX, tlY)
  
  /** Function to return the middle left coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the middle left part of the graphic
   * 
   */ 
  def ml: (Double,Double) = (tlX, tlY + height/2)
  
  /** Function to return the bottom left coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the bottom left part of the graphic
   * 
   */ 
  def bl: (Double,Double) = (tlX, tlY + height)
  
  /** Function to return the top center coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the top center part of the graphic
   * 
   */ 
  def tc: (Double,Double) = (tlX + width/2, tlY)
  
  /** Function to return the middle center coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the middle center part of the graphic
   * 
   */ 
  def mc: (Double,Double) = (tlX + width/2, tlY + height/2)
  
  /** Function to return the center coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the center part of the graphic
   * 
   */     
  def c: (Double,Double) = mc
  
  /** Function to return the bottom center coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the bottom center part of the graphic
   * 
   */ 
  def bc: (Double,Double) = (tlX + width/2, tlY + height)
  
  /** Function to return the top right coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the top right part of the graphic
   * 
   */ 
  def tr: (Double,Double) = (tlX + width, tlY)
  
  /** Function to return the middle right coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the middle right part of the graphic
   * 
   */   
  def mr: (Double,Double) = (tlX + width, tlY + height/2)
  
  /** Function to return the bottom right coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the bottom right part of the graphic
   * 
   */ 
  def br: (Double,Double) = (tlX + width, tlY + height)
    
  /* ========= Bounds methods ========= */
  
  /** Function takes a shape and returns the same shape with a zero size bounding box
   * 
   * @return Graphic with zero sized bounding box
   * 
   */ 
  def smash: Graphic = BoundsChanger(this)
  
  /** Function takes a shape and returns the same shape with a bounding box with width = 0
   * 
   * @return Graphic with bounding box of width = 0
   * 
   */
  def smashWidth: Graphic = BoundsChanger(this, 0, height)
  
  /** Function takes a shape and returns the same shape with a bounding box with height = 0
   * 
   * @return Graphic with bounding box of height = 0
   * 
   */
  def smashHeight: Graphic = BoundsChanger(this, width, 0)
  
  /** Function to changing the bounding box of the graphic
   *  
   * @param newWidth Int which is the new Width of the bounding box
   * @param newHeight Int which is the new Height of the bounding box
   * @param moveX  Int optional parameter to move the graphic to this x coordinate
   * @param moveY  Int optional parameter to move the graphic to this y coordinate
   * @return Graphic with a modified bounding box.
   *
   * 
  */
  def changeBounds(newWidth: Double, newHeight: Double, moveX: Double = 0, moveY: Double = 0): Graphic = {
    BoundsChanger(this, newWidth, newHeight, moveX, moveY)
  }
  
  /** Method to add padding around a Graphic
 * @param top amount to pad above the graphic
 * @param left amount to pad left of the graphic
 * @param bottom amount to pad below the graphic
 * @param right amount to pad right of the graphic
 * @return Graphic with a padded bounding box
 */
  def pad(top: Double, left: Double, bottom: Double, right: Double): Graphic = {
    BoundsChanger(this, width + left + right, height + top + bottom, -left, -top)
  }
  
  /**
   * @param value amount to pad in every direction around a graphic
   * @return Graphic with uniformly padded bounding box
   */
  def pad(value: Double): Graphic = pad(value, value, value, value)
  
  /**
 * @param v amount to pad above and below the graphic
 * @param h amount to pad left and right of the graphic
 * @return Graphic with padded bounding box
 */
  def pad(v: Double, h: Double): Graphic = pad(v, h, v, h)
  
  def padTop(top: Double): Graphic = pad(top, 0, 0, 0)
  def padLeft(left: Double): Graphic = pad(0, left, 0, 0)
  def padBottom(bottom: Double): Graphic = pad(0, 0, bottom, 0)
  def padRight(right: Double): Graphic = pad(0, 0, 0, right)
  
  /**
   * @param value amount to pad left and right of the graphic
   * @return Graphic equally padded on its left and right sides
   */
  def padH(value: Double): Graphic = pad(0, value, 0, value)
  
  /**
   * @param value amount to pad above and below the graphic
   * @return Graphic equally padded on its top and bottom sides
   */
  def padV(value: Double): Graphic = pad(value, 0, value, 0)
  
  /** Function to freeze the graphic and get its pixel based graphic
   * 
   * @return Graphic which is pixel based.
   * 
   */
  def freeze: Graphic = Freeze(this)
}
