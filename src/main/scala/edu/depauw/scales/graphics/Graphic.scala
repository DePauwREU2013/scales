package edu.depauw.scales.graphics

import java.awt.geom.{AffineTransform, Rectangle2D => jRect}
import java.awt.{Shape => jShape}


/**This class defines the general member functions of any Graphic
 * 
 * @author RudraVishweshwar
 *
 */
trait Graphic {
  /**
   * @param gc GraphicsContext
   * @return returns the graphic on the panel
   * 
   */
  def render(gc : GraphicsContext)
  

  lazy val bounds: jRect = new jRect.Double()
  
  lazy val shape: jShape = bounds
  /**function to name the Graphic
   * 
   * @param name String containing the name for the graphic
   * @return nil
   * 
   */
  def withName(name: String): List[Graphic]
  
  /* ========= Compositing methods ========= */
  /** Function to put a Graphic over another Graphic
   * 
   * @param g Graphic to be placed Over
   * @return Graphic with the new composite
   * 
   */
  def over(g : Graphic) : Graphic = Composite(this, g)
  /** Shortcut function for over
   * 
   * @param g Graphic to be placed Over
   * @return Graphic with the new composite
   * 
   */
  def -&(g : Graphic) : Graphic = this over g
  
  /**This function is an alias for backward compatibility
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
    Composite(this,
      Translate(this.bounds.getX + this.bounds.getWidth - g.bounds.getX, 0, g))
  }
  /** This function is an alias for backward compatibility
   * 
   * @param g Graphic to be placed beside
   * @return Graphic with the new composite
   * 
   */
  def |||(g: Graphic): Graphic = this beside g
  /** Shortcut function for beside
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
      Translate(0, this.bounds.getY + this.bounds.getHeight - g.bounds.getY, g))
  }
  
  /** Shortcut Function for above
   * 
   * @param g Graphic to be placed above
   * @return Graphic with the new composite
   * 
   */
    def -^(g: Graphic): Graphic = this above g
  
  /* ========= Alignment methods ========= */
  /** Function to move a graphic on the panel
   * 
   * @param x the x coordinate of the position to be moved to
   * @param y the y coordinate of the position to be moved to
   * @return Graphic at the new position
   * 
   */
  def moveTo(x: Double, y: Double): Graphic = {
    Translate(x - this.bounds.getX, y - this.bounds.getY, this)
  }
  /** Shortcut function for move
   * 
   * @param x the x coordinate of the position to be moved to
   * @param y the y coordinate of the position to be moved to
   * @return Graphic at the new position
   * 
   */
  def -@(x: Double, y: Double): Graphic = moveTo(x,y)
  
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
  def middleLeft: Graphic = moveTo(0, -this.bounds.getHeight/2)
  /** Function to move a graphic to the bottom left position
   * 
   * @param nil
   * @return Graphic at the top bottom left position
   * 
   */
  def bottomLeft: Graphic = moveTo(0, -this.bounds.getHeight)
  /** Function to move a graphic to the top center position
   * 
   * @param nil
   * @return Graphic at the top center position
   * 
   */
  def topCenter: Graphic = moveTo(-this.bounds.getWidth/2, 0)
  /** Function to move a graphic to the middle center position
   * 
   * @param nil
   * @return Graphic at the middle center position
   * 
   */
  def middleCenter: Graphic =
    moveTo(-this.bounds.getWidth/2, -this.bounds.getHeight/2)
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
    moveTo(-this.bounds.getWidth/2, -this.bounds.getHeight)
  /** Function to move a graphic to the top right position
   * 
   * @param nil
   * @return Graphic at the top right position
   * 
   */
  def topRight: Graphic = moveTo(-this.bounds.getWidth, 0)
  /** Function to move a graphic to the middle right position
   * 
   * @param nil
   * @return Graphic at the middle right position
   * 
   */  
  def middleRight: Graphic =
    moveTo(-this.bounds.getWidth, -this.bounds.getHeight/2)
  /** Function to move a graphic to the bottom right position
   * 
   * @param nil
   * @return Graphic at the bottom right position
   * 
   */
  def bottomRight: Graphic = 
    moveTo(-this.bounds.getWidth, -this.bounds.getHeight)
    
    //======= Point returning methods ========
  /** Function to return the upper left coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the upper left part of the graphic
   * 
   */  
  def ul: (Double,Double) = (this.bounds.getX(), this.bounds.getY())
  /** Function to return the middle left coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the middle left part of the graphic
   * 
   */ 
  def ml: (Double,Double) = (this.bounds.getX(),this.bounds.getY()+(this.bounds.getBounds().height/2))
  
  /** Function to return the bottom left coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the bottom left part of the graphic
   * 
   */ 
  def bl: (Double,Double) = (this.bounds.getX(), this.bounds.getY()+this.bounds.getBounds().height)
  /** Function to return the top center coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the top center part of the graphic
   * 
   */ 
  def tc: (Double,Double) = (this.bounds.getX()+(this.bounds.getBounds().width/2),this.bounds.getY())
  /** Function to return the middle center coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the middle center part of the graphic
   * 
   */ 
  def mc: (Double,Double) = (this.bounds.getX()+(this.bounds.getBounds().width/2),
      this.bounds.getY()+(this.bounds.getBounds.height/2))
  
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
  def bc: (Double,Double) = (this.bounds.getX()+(this.bounds.getBounds.width/2),this.bounds.getY+this.bounds.getBounds.height)
  /** Function to return the top right coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the top right part of the graphic
   * 
   */ 
  def tr: (Double,Double) = (this.bounds.getX()+this.bounds.getBounds.width,this.bounds.getY())
  /** Function to return the middle right coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the middle right part of the graphic
   * 
   */   
  def mr: (Double,Double) = (this.bounds.getX()+this.bounds.getBounds.width,this.bounds.getY()+(this.bounds.getBounds.height/2))
  
  /** Function to return the bottom right coordinates of the graphic
   * 
   * @param nil
   * @return (Double, Double) the coordinates of the bottom right part of the graphic
   * 
   */ 
  def br: (Double,Double) = (this.bounds.getX()+this.bounds.getBounds.width,this.bounds.getY()+this.bounds.getBounds.height)
    
  /* ========= Bounds methods ========= */
  
  
  /** Function takes a shape and returns the same shape with a zero size bounding box
   * 
   * @param nil
   * @return Graphic with zero sized bounding box
   * 
   */ 
  def smash: Graphic = BoundsChanger(this)
  /** Function takes a shape and returns the same shape with a bounding box with width = 0
   * 
   * @param nil
   * @return Graphic with bounding box of width = 0
   * 
   */
  def vSmash: Graphic = BoundsChanger(this,0,this.bounds.getHeight)
  /** Function takes a shape and returns the same shape with a bounding box with height = 0
   * 
   * @param nil
   * @return Graphic with bounding box of height = 0
   * 
   */
  def hSmash: Graphic = BoundsChanger(this,this.bounds.getWidth,0)
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
  def changeBounds(newWidth: Int, newHeight: Int, moveX: Int = 0, moveY: Int = 0): Graphic = {
    BoundsChanger(this, newWidth, newHeight, moveX, moveY)
  }
  
  /** Function to freeze the graphic and get its pixel based graphic
   * 
   * @param nil
   * @return Graphic which is pixel based.
   * 
   */
  def freeze : Graphic = Freeze(this)
}
