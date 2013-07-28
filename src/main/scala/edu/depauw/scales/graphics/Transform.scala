package edu.depauw.scales.graphics

import java.awt.geom.AffineTransform
import edu.depauw.scales.{Step, TransformSheet}

/**
 * Wrapper to specify an arbitrary transformation of a given `Graphic` instance.
 */
case class Transform(transform: AffineTransform, g: Graphic) extends Graphic {
  
  /**
   * renders graphic with specified transformation
   */
  def render(gc : GraphicsContext) {
    // keep old transform ref
    val oldTransform = gc.g2d.getTransform
    
    // apply specified transform
    gc.g2d.transform(transform)
    
    // render graphic using it
    g.render(gc)
    
    // restore old transform
    gc.g2d.setTransform(oldTransform)
  }
  
  /** @return bounds of the transformed `shape` */
  override lazy val bounds = shape.getBounds2D
  
  /** @return transformed `shape` */
  override lazy val shape = transform.createTransformedShape(g.shape)
  
  /** 
   *  @return transformed instances of all name-matched graphics in the original graphic
   */
  def withName(n: String) = g.withName(n).map(Transform(transform, _))
}


object Rotate {
  /**
   * Rotates graphic about (0,0)
   * @param theta angle of rotation in radians
   * @param g graphic to rotate
   */
  def apply(theta: Double, g: Graphic) =
    Transform(AffineTransform.getRotateInstance(theta), g)
    
  /**
   * Rotates graphic about (x,y)
   * @param theta angle of rotation in radians
   * @param x horizontal component of rotation anchor point
   * @param y vertical component of rotation anchor point
   * @param g graphic to rotate
   */
  def apply(theta: Double, x: Double, y: Double, g: Graphic) =
    Transform(AffineTransform.getRotateInstance(theta, x, y), g)
  
  def apply(theta: Double, step: Step) =
    TransformSheet(AffineTransform.getRotateInstance(theta), step)
  def apply(theta: Double, x: Double, y: Double, step: Step) =
    TransformSheet(AffineTransform.getRotateInstance(theta, x, y), step)
}


object Scale {
  /**
   * Scales graphic
   * @param sx horizontal component of scaling
   * @param sy vertical component of scaling
   * @param g graphic to scale
   */
  def apply(sx: Double, sy: Double, g: Graphic): Transform =
    Transform(AffineTransform.getScaleInstance(sx, sy), g)
    
  /**
   * Scales graphic proportionally
   * @param s amount to scale
   * @param g graphic to scale
   */
  def apply(s: Double, g: Graphic): Transform = apply(s, s, g)
  
  def apply(sx: Double, sy: Double, step: Step) =
    TransformSheet(AffineTransform.getScaleInstance(sx, sy), step)
}

object Shear {
  /**
   * Shears graphic
   * @param sx horizontal component of shear
   * @param sy vertical component of shear
   * @param g graphic to shear
   */
  def apply(sx: Double, sy: Double, g: Graphic) =
    Transform(AffineTransform.getShearInstance(sx, sy), g)
  
  def apply(sx: Double, sy: Double, step: Step) =
    TransformSheet(AffineTransform.getShearInstance(sx, sy), step)
}

object Translate {
  
  /**
   * Translates graphic
   * @param x horizontal component of translation
   * @param y vertical component of translation
   * @param g graphic to translate
   */
  def apply(x: Double, y: Double, g: Graphic) =
    Transform(AffineTransform.getTranslateInstance(x, y), g)
    
  def apply(x: Double, y: Double, step: Step) =
    TransformSheet(AffineTransform.getTranslateInstance(x, y), step)
}
