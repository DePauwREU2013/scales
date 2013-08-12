package edu.depauw.scales
package graphics

import java.awt.geom.AffineTransform

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
  override lazy val bounds = transform.createTransformedShape(g.bounds).getBounds2D
  
  /** @return transformed `shape` */
  override lazy val shape = transform.createTransformedShape(g.shape)
  
  /** 
   *  @return transformed instances of all name-matched graphics in the original graphic
   */
  def withName(n: String) = g.withName(n).map(Transform(transform, _))
  
  // Special case optimization to merge two transforms into one
  override def transform(t2: AffineTransform): Graphic = {
    val t = new AffineTransform(transform)
    t.preConcatenate(t2)
    Transform(t, g)
  }
}


object Rotate {
  /**
   * Rotates graphic about (0,0)
   * @param theta angle of rotation
   * @param g graphic to rotate
   */
  def apply(theta: Angle, g: Graphic): Graphic =
    g.transform(AffineTransform.getRotateInstance(theta.inRadians))
    
  /**
   * Rotates graphic about (x,y)
   * @param theta angle of rotation
   * @param x horizontal component of rotation anchor point
   * @param y vertical component of rotation anchor point
   * @param g graphic to rotate
   */
  def apply(theta: Angle, x: Double, y: Double, g: Graphic): Graphic =
    g.transform(AffineTransform.getRotateInstance(theta.inRadians, x, y))
  
  def apply(theta: Angle, step: Step): Step =
    TransformSheet(AffineTransform.getRotateInstance(theta.inRadians), step)
  def apply(theta: Angle, x: Double, y: Double, step: Step): Step =
    TransformSheet(AffineTransform.getRotateInstance(theta.inRadians, x, y), step)
}


object Scale {
  /**
   * Scales graphic
   * @param sx horizontal component of scaling
   * @param sy vertical component of scaling
   * @param g graphic to scale
   */
  def apply(sx: Double, sy: Double, g: Graphic): Graphic =
    g.transform(AffineTransform.getScaleInstance(sx, sy))
    
  /**
   * Scales graphic proportionally
   * @param s amount to scale
   * @param g graphic to scale
   */
  def apply(s: Double, g: Graphic): Graphic = apply(s, s, g)
  
  def apply(sx: Double, sy: Double, step: Step): Step =
    TransformSheet(AffineTransform.getScaleInstance(sx, sy), step)
}

object Shear {
  /**
   * Shears graphic
   * @param sx horizontal component of shear
   * @param sy vertical component of shear
   * @param g graphic to shear
   */
  def apply(sx: Double, sy: Double, g: Graphic): Graphic =
    g.transform(AffineTransform.getShearInstance(sx, sy))
  
  def apply(sx: Double, sy: Double, step: Step): Step =
    TransformSheet(AffineTransform.getShearInstance(sx, sy), step)
}

object Translate {
  
  /**
   * Translates graphic
   * @param x horizontal component of translation
   * @param y vertical component of translation
   * @param g graphic to translate
   */
  def apply(x: Double, y: Double, g: Graphic): Graphic =
    g.transform(AffineTransform.getTranslateInstance(x, y))
    
  def apply(x: Double, y: Double, step: Step): Step =
    TransformSheet(AffineTransform.getTranslateInstance(x, y), step)
}
