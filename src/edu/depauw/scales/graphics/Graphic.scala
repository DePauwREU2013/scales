package edu.depauw.scales.graphics

import java.awt.geom.{Rectangle2D => jRect}
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
          moveTo(this.bounds.getX + this.bounds.getWidth, this.bounds.getY, _)) : _*)
    }
    case _ => {
      Composite(this, moveTo(this.bounds.getX + this.bounds.getWidth, this.bounds.getY, g))
    }
  }
  
  def |||(g: Graphic): Graphic = this beside g
  
  def above(g: Graphic): Graphic = g match {
    case blank: Blank => this
    case composite : Composite => {
      Composite(List(this) ++ composite.children.map(
          moveTo(this.bounds.getX, this.bounds.getY + this.bounds.getHeight, _)) : _*)
    }
    case _ => {
    Composite(this, moveTo(this.bounds.getX, this.bounds.getY + this.bounds.getHeight, g))
    }
  }
  
  def -^(g: Graphic): Graphic = this above g
  
  def center = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, -g.bounds.getWidth/2, -g.bounds.getHeight/2), g)
    }
    case _ => Translate(-this.bounds.getWidth/2, -this.bounds.getHeight/2, this)
  }
  
  def lowerLeft = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, 0, -g.bounds.getHeight), g)
    }
    case _ => Translate(0, -this.bounds.getHeight, this)
  }
  
  def topLeft = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, 0, 0), g)
    }
    case _ => Translate(0, 0, this)
  }
  
  def lowerRight = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, -g.bounds.getWidth, -g.bounds.getHeight), g)
    }
    case _ => Translate(-this.bounds.getWidth, -this.bounds.getHeight, this)
  }
  
  def topRight = this match {
    case Transform(t,g) => {
      Transform(replaceTranslate(t, -g.bounds.getWidth, 0), g)
    }
    case _ => Translate(-this.bounds.getWidth, 0, this)
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
  def vSmash: Graphic = BoundsChanger(this,0,this.bounds.getHeight)
  def hSmash: Graphic = BoundsChanger(this,this.bounds.getWidth,0)
  /*
   * Returns a graphic with a modified bounding box. 
   * Requires a new width and height for the bounding box
   * Optional parameters move the position of the bounding box as well
  */
  def changeBounds(newWidth: Int, newHeight: Int, moveX: Int = 0, moveY: Int = 0): Graphic = {
    BoundsChanger(this, newWidth, newHeight, moveX, moveY)
  }
  
  def moveTo(x: Double, y: Double, g: Graphic): Graphic = {
    Translate(x - g.bounds.getX, y - g.bounds.getY, g)
  }
}
