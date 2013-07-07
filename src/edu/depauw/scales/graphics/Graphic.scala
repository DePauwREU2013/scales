package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}
import java.awt.geom.AffineTransform

trait Graphic {
  def render(gc : GraphicsContext)
  
  def bounds: jRect
  
  def -&(g : Graphic) : Graphic = g match {
    case blank: Blank => this
    case composite : Composite => Composite(List(this) ++ composite.children : _*)
    case _ => Composite(this, g)
  }
  
  // this is an alias for backward compatibility
  def |(g : Graphic) : Graphic = this -& g
  
  def |||(g : Graphic) : Graphic = g match {
    case blank: Blank => this
    case composite : Composite => {
      Composite(List(this) ++ composite.children.map(
          Translate(this.bounds.x + this.bounds.width, this.bounds.y, _)) : _*)
    }
    case _ => {
      Composite(this, Translate(this.bounds.x + this.bounds.width, this.bounds.y, g))
    }
  }
  
  def -^(g: Graphic): Graphic = g match {
    case blank: Blank => this
    case composite : Composite => {
      Composite(List(this) ++ composite.children.map(
          Translate(this.bounds.x, this.bounds.y + this.bounds.height, _)) : _*)
    }
    case _ => {
      Composite(this, Translate(this.bounds.x, this.bounds.y + this.bounds.height, g))
    }
  }
  
  def center = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, -g.bounds.width/2, -g.bounds.height/2), g)
    }
    case _ => Translate(-this.bounds.width/2, -this.bounds.height/2, this)
  }
  
  def lowerLeft = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, 0, -g.bounds.height), g)
    }
    case _ => Translate(0, -this.bounds.height, this)
  }
  
  def topLeft = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, 0, 0), g)
    }
    case _ => Translate(0, 0, this)
  }
  
  def lowerRight = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, -g.bounds.width, -g.bounds.height), g)
    }
    case _ => Translate(-this.bounds.width, -this.bounds.height, this)
  }
  
  def topRight = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, -g.bounds.width, 0), g)
    }
    case _ => Translate(-this.bounds.width, 0, this)
  }
  
  def replaceTranslate(transform: AffineTransform, newX:Double, newY:Double): AffineTransform = {
    var newTransform: AffineTransform = transform.clone().asInstanceOf[AffineTransform]
    newTransform.translate(
      newX - transform.getTranslateX(),
      newY - transform.getTranslateY()
    )
    newTransform
  }
    /*
   * Takes a shape and returns the same shape with a modified bounding box
   * smash: zero size bounding box
   * vSmash: Bounding box with width = 0
   * hSmash: Bounding box with height = 0
   */
  def smash: Graphic = Smasher(this)
  def vSmash: Graphic = VSmasher(this)
  def hSmash: Graphic = HSmasher(this)
  
  
}
