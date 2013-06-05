package edu.depauw.scales.graphics

import java.awt.geom.{Ellipse2D,Rectangle2D,GeneralPath,Line2D}

case class Shape(shape : java.awt.Shape) extends Graphic {
  def render(gc : GraphicsContext) {
    gc.drawShape(shape)
  }
}

object Circle {
  // (x, y) is at the center
  def apply(x : Double, y : Double, radius : Double) =
    Shape(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius))
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

object Line {
  def apply(x1 : Double, y1 : Double, x2 : Double, y2 : Double) = 
    Shape(new Line2D.Double(x1, y1, x2, y2))
}

//TODO: rounded rectangles, arcs, other curves, areas?
