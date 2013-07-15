package edu.depauw.scales.graphics

import java.awt.geom.{AffineTransform, Rectangle2D => jRect}
import java.awt.{Shape => jShape}


trait Graphic {
  def render(gc : GraphicsContext)
  
  lazy val bounds: jRect = new jRect.Double()
  
  lazy val shape: jShape = bounds
  
  def withName(name: String): List[Graphic]
  
  /* ========= Compositing methods ========= */
  
  def over(g : Graphic) : Graphic = Composite(this, g)
  
  def -&(g : Graphic) : Graphic = this over g
  
  // this is an alias for backward compatibility
  def |(g : Graphic) : Graphic = this over g
  
  def beside(g : Graphic) : Graphic = {
    Composite(this,
      Translate(this.bounds.getX + this.bounds.getWidth - g.bounds.getX, 0, g))
  }
  
  def |||(g: Graphic): Graphic = this beside g
  def -||(g: Graphic): Graphic = this beside g
  
  def above(g: Graphic): Graphic = {
    Composite(this,
      Translate(0, this.bounds.getY + this.bounds.getHeight - g.bounds.getY, g))
  }
  
  def -^(g: Graphic): Graphic = this above g
  
  /* ========= Alignment methods ========= */
  
  def moveTo(x: Double, y: Double): Graphic = {
    Translate(x - this.bounds.getX, y - this.bounds.getY, this)
  }
  
  def -@(x: Double, y: Double): Graphic = moveTo(x,y)
  
  def topLeft: Graphic = moveTo(0, 0)
  
  def middleLeft: Graphic = moveTo(0, -this.bounds.getHeight/2)
  
  def bottomLeft: Graphic = moveTo(0, -this.bounds.getHeight)
  
  def topCenter: Graphic = moveTo(-this.bounds.getWidth/2, 0)
  
  def middleCenter: Graphic =
    moveTo(-this.bounds.getWidth/2, -this.bounds.getHeight/2)
  
  def center: Graphic = middleCenter
  
  def bottomCenter: Graphic =
    moveTo(-this.bounds.getWidth/2, -this.bounds.getHeight)
  
  def topRight: Graphic = moveTo(-this.bounds.getWidth, 0)
    
  def middleRight: Graphic =
    moveTo(-this.bounds.getWidth, -this.bounds.getHeight/2)
  
  def bottomRight: Graphic = 
    moveTo(-this.bounds.getWidth, -this.bounds.getHeight)
    
    //======= Point returning methods ========
    
  def ul: (Double,Double) = (this.bounds.getX(), this.bounds.getY())
  
  def ml: (Double,Double) = (this.bounds.getX(),this.bounds.getY()+(this.bounds.getBounds().height/2))
  
  def bl: (Double,Double) = (this.bounds.getX(), this.bounds.getY()+this.bounds.getBounds().height)
  
  def tc: (Double,Double) = (this.bounds.getX()+(this.bounds.getBounds().width/2),this.bounds.getY())
  
  def mc: (Double,Double) = (this.bounds.getX()+(this.bounds.getBounds().width/2),
      this.bounds.getY()+(this.bounds.getBounds.height/2))
  
  def c: (Double,Double) = mc
  
  def bc: (Double,Double) = (this.bounds.getX()+(this.bounds.getBounds.width/2),this.bounds.getY+this.bounds.getBounds.height)
  
  def tr: (Double,Double) = (this.bounds.getX()+this.bounds.getBounds.width,this.bounds.getY())
    
  def mr: (Double,Double) = (this.bounds.getX()+this.bounds.getBounds.width,this.bounds.getY()+(this.bounds.getBounds.height/2))
  
  def br: (Double,Double) = (this.bounds.getX()+this.bounds.getBounds.width,this.bounds.getY()+this.bounds.getBounds.height)
    
  /* ========= Bounds methods ========= */
  
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
   def freeze : Graphic = Freeze(this)
}
