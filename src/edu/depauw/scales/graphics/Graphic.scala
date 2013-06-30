package edu.depauw.scales.graphics

import java.awt.{Rectangle => jRect}
import java.awt.geom.AffineTransform

trait Graphic {
  def render(gc : GraphicsContext)
  
  def |(g : Graphic) : Graphic = g match {
    case Blank => this
    case over : Over => Over(List(this) ++ over.children : _*)
    case _ => Over(this, g)
  }
  
  def -&(g : Graphic) : Graphic = this | g
  
  def |||(g : Graphic) : Graphic = g match {
    case Blank => this
    case beside : Beside => Beside(List(this) ++ beside.children : _*)
    case _ => Beside(this, g)
  }
  
  def ^(g: Graphic): Graphic = g match {
    case Blank => this
    case above: Above => Above(List(this) ++ above.children: _*)
    case _ => Above(this,g)
  }
  
  def bounds: jRect
  
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
  
}
