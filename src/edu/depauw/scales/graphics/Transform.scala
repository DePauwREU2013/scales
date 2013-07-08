package edu.depauw.scales.graphics

import edu.depauw.scales._

import java.awt.geom.AffineTransform

case class Transform(transform : AffineTransform, g : Graphic) extends Graphic {
  def render(gc : GraphicsContext) {
    val oldTransform = gc.g2d.getTransform
    gc.g2d.transform(transform)
    g.render(gc)
    gc.g2d.setTransform(oldTransform)
  }
  
  def bounds = transform.createTransformedShape(g.bounds).getBounds2D
}

object Rotate {
  def apply(theta : Double, g : Graphic) =
    Transform(AffineTransform.getRotateInstance(theta), g)
  def apply(theta : Double, x : Double, y : Double, g : Graphic) =
    Transform(AffineTransform.getRotateInstance(theta, x, y), g)
  def apply(theta : Double, step : Step) =
    TransformSheet(AffineTransform.getRotateInstance(theta), step)
  def apply(theta : Double, x : Double, y : Double, step : Step) =
    TransformSheet(AffineTransform.getRotateInstance(theta, x, y), step)
}

object Scale {
  def apply(sx : Double, sy : Double, g : Graphic) =
    Transform(AffineTransform.getScaleInstance(sx, sy), g)
  def apply(sx : Double, sy : Double, x : Double, y : Double, g : Graphic) = {
    val transform = AffineTransform.getTranslateInstance(x, y)
    transform.scale(sx, sy)
    transform.translate(-x, -y)
    Transform(transform, g)
  }
  def apply(sx : Double, sy : Double, step : Step) =
    TransformSheet(AffineTransform.getScaleInstance(sx, sy), step)
  def apply(sx : Double, sy : Double, x : Double, y : Double, step : Step) = {
    val transform = AffineTransform.getTranslateInstance(x, y)
    transform.scale(sx, sy)
    transform.translate(-x, -y)
    TransformSheet(transform, step)
  }
}

object Shear {
  def apply(sx : Double, sy : Double, g : Graphic) =
    Transform(AffineTransform.getShearInstance(sx, sy), g)
  def apply(sx : Double, sy : Double, x : Double, y : Double, g : Graphic) = {
    val transform = AffineTransform.getTranslateInstance(x, y)
    transform.shear(sx, sy)
    transform.translate(-x, -y)
    Transform(transform, g)
  }
  def apply(sx : Double, sy : Double, step : Step) =
    TransformSheet(AffineTransform.getShearInstance(sx, sy), step)
  def apply(sx : Double, sy : Double, x : Double, y : Double, step : Step) = {
    val transform = AffineTransform.getTranslateInstance(x, y)
    transform.shear(sx, sy)
    transform.translate(-x, -y)
    TransformSheet(transform, step)
  }
}

object Translate {
  def apply(x : Double, y : Double, g : Graphic) =
    Transform(AffineTransform.getTranslateInstance(x, y), g)
  def apply(x : Double, y : Double, step : Step) =
    TransformSheet(AffineTransform.getTranslateInstance(x, y), step)
}
