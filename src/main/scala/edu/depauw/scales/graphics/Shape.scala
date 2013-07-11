package edu.depauw.scales.graphics

import java.awt.geom.{Ellipse2D,Rectangle2D,GeneralPath,Line2D,RoundRectangle2D}

case class Shape(jShape : java.awt.Shape) extends Graphic {
  def render(gc : GraphicsContext) {
    gc.drawShape(jShape)
  }
  
  def bounds = jShape.getBounds2D()
  
  override lazy val shape = jShape
  
  def withName(name: String) = Nil
}

object Circle {
  // (x, y) is at the center
  def apply(radius : Double, x : Double, y : Double) =
    Shape(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius))
  
  // circle with upper-left at (0,0)
  def apply(radius:Double) = 
    Shape(new Ellipse2D.Double(0, 0, 2 * radius, 2 * radius))
}

object Ellipse {
  // (x, y) is at the upper-left of the bounding box
  def apply(x : Double, y : Double, xDiam : Double, yDiam : Double) =
    Shape(new Ellipse2D.Double(x, y, xDiam, yDiam))
}

object Rectangle {
  // (x, y) is at the upper-left
  def apply(x : Double, y : Double, width : Double, height : Double) =
    Shape(new Rectangle2D.Double(x, y, width, height))
}

object Square {
  // (x, y) is at the center
  def apply(x : Double, y : Double, width : Double) =
    Shape(new Rectangle2D.Double(x - width / 2, y - width / 2, width, width))
  
  def apply(width: Double) = Shape(new Rectangle2D.Double(0, 0, width, width))
}

object Polygon {
  def apply(points : (Double, Double)*) = {
    val poly : GeneralPath = new GeneralPath()
    
    val first = points.head
    poly.moveTo(first._1.toFloat, first._2.toFloat)
    for ((x, y) <- points.drop(1)) {
      poly.lineTo(x.toFloat, y.toFloat)
    }
    poly.closePath()
    
    Shape(poly)
  }
}
/*
 * Same as a Polygon, but it is not a closed shape. 
 */
object Path {
  def apply(points: (Double,Double)*) = {
    val path : GeneralPath = new GeneralPath()
    
    val first = points.head
    path.moveTo(first._1.toFloat, first._2.toFloat)
    for ((x, y) <- points.drop(1)) {
      path.lineTo(x.toFloat, y.toFloat)
    }
    Shape(path)
  }
}
/*
 * Work in progress:
 * A curved path that uses curves instead of straight lines
 * This is NOT finished, it needs quite a bit of thought/work
 */
object BezierPath {
  def apply(points: (Double,Double)*) = {
    if(points.size < 3) Path()
    val path: GeneralPath = new GeneralPath()
    val first = points.head
    path.moveTo(first._1.toFloat,first._2.toFloat)
    while(points.drop(1).size > 2){
      /*
       * There are two good ways to go about this. The first is to use a Bezier curve,
       * and the second is to use a quadratic curve. I need to think of a way to generate
       * some kind of control scheme to get the curve to work correctly, either by taking
       * control points as inputs or finding a way to generate them. Will complete tomorrow.
       */
    }
    	
  }
}

object Line {
  def apply(x1 : Double, y1 : Double, x2 : Double, y2 : Double) = 
    Shape(new Line2D.Double(x1, y1, x2, y2))
}

object RoundRectangle {
  def apply(x: Double, y: Double, w: Double, h: Double, arcw: Double, arch: Double) =
    Shape(new RoundRectangle2D.Double(x,y,w,h,arcw,arch))
}
//TODO: , arcs, other curves, areas?

