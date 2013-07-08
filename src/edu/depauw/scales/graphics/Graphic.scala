package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}
import java.awt.geom.AffineTransform


trait Graphic {
  def render(gc : GraphicsContext)
  
  def bounds: jRect
  
  def over(g : Graphic) : Graphic = g match {
    case blank: Blank => this
    case composite : Composite => Composite(List(this) ++ composite.children : _*)
    case _ => Composite(this, g)
  }
  
  def -&(g : Graphic) : Graphic = this over g
  
  // this is an alias for backward compatibility
  def |(g : Graphic) : Graphic = this over g
  
  def beside(g : Graphic) : Graphic = g match {
    case blank: Blank => this
    case composite : Composite => {
      Composite(List(this) ++ composite.children.map(
          moveTo(this.bounds.x + this.bounds.width, this.bounds.y, _)) : _*)
    }
    case _ => {
      Composite(this, moveTo(this.bounds.x + this.bounds.width, this.bounds.y, g))
    }
  }
  
  def |||(g: Graphic): Graphic = this beside g
  
  def above(g: Graphic): Graphic = g match {
    case blank: Blank => this
    case composite : Composite => {
      Composite(List(this) ++ composite.children.map(
          moveTo(this.bounds.x, this.bounds.y + this.bounds.height, _)) : _*)
    }
    case _ => {
    Composite(this, moveTo(this.bounds.x, this.bounds.y + this.bounds.height, g))
    }
  }
  
  def -^(g: Graphic): Graphic = this above g
  
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
  def smash: Graphic = BoundsChanger(this)
  def vSmash: Graphic = BoundsChanger(this,0,this.bounds.height)
  def hSmash: Graphic = BoundsChanger(this,this.bounds.width,0)
  /*
   * Returns a graphic with a modified bounding box. 
   * Requires a new width and height for the bounding box
   * Optional parameters move the position of the bounding box as well
  */
  def changeBounds(newWidth: Int, newHeight: Int, moveX: Int = 0, moveY: Int = 0): Graphic = {
    BoundsChanger(this, newWidth, newHeight, moveX, moveY)
  }
  
  def moveTo(x: Double, y: Double, g: Graphic): Graphic = {
    Translate(x - g.bounds.x, y - g.bounds.y, g)
  }
}
