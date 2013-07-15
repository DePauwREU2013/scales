package edu.depauw.scales.graphics

import java.awt.geom.{Ellipse2D,Rectangle2D,GeneralPath,Line2D,RoundRectangle2D}

case class Shape(jShape : java.awt.Shape) extends Graphic {
  def render(gc : GraphicsContext) {
    gc.drawShape(jShape)
  }
  
  override lazy val bounds = jShape.getBounds2D
  
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
    poly.moveTo(first._1, first._2)
    for ((x, y) <- points.drop(1)) {
      poly.lineTo(x, y)
    }
    poly.closePath()
    
    Shape(poly)
  }
}
/*
 * Same as a Polygon, but it is not a closed shape. 
 */
object StraightPath {
  def apply(points: (Double,Double)*) = {
    val path : GeneralPath = new GeneralPath()
    
    val first = points.head
    path.moveTo(first._1, first._2)
    for ((x, y) <- points.drop(1)) {
      path.lineTo(x, y)
    }
    Shape(path)
  }
}
/*
 * Work in progress:
 * A curved path that uses curves instead of straight lines
 * This is NOT finished, it needs quite a bit of thought/work
 */
object Path {
	def apply {
	  
	}
}
/*
 * ControlledBezierPath allows you to create a series of curves with specific control
 * points as opposed to the planned automatic curve generation in CurvedPath
 * Points are given to the program in an alternating pattern of Drawn points
 * and control points, starting and ending with drawn points.
 * (draw,control,control,draw,control,control,draw)
 */

object ControlledBezierPath {
  def apply(points: (Double,Double)*) = {
    val path: GeneralPath = new GeneralPath()
    val first = points.head
    path.moveTo(first._1,first._2)
    aux(points.drop(1).toList)
    def aux(points: List[(Double,Double)]): Unit = points match{
      case control1::control2::end::rest => 
        path.curveTo(control1._1,control1._2,control2._1,control2._2,end._1,end._2)
        aux(rest)
      case _ => {}
    }
    Shape(path)
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

