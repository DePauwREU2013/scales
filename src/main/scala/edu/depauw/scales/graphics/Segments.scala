package edu.depauw.scales.graphics

import scala.language.implicitConversions

/*
 * contains the curveTo and lineTo methods.
 * Segment uses a data structure where the previous segment is stored as a value
 * in the current segment, allowing the convenient .lineTo and .curveTo language
 * Read Path's documentation for more information on how to use Segments practially
 */
trait Segment {
	val x,y,heading: Double
	def lineTo(x: Double,y:Double): Segment = new LineSegment(this,x,y)
	def curveTo(x:Double,y:Double):Segment = new CurveSegment(this,x,y)
	def heading(h:Double):Segment
}
/*
 * A segment that draws a straight line to the given point from the previous point
 */

case class LineSegment(val s: Segment,val x: Double,val y: Double,val heading:Double = 0) extends Segment{
  def heading(h:Double):Segment = new LineSegment(s,x,y,h)
}
//A segment that draws a bezier curve to the next point from the previous point
//using both its heading and the heading of the following segment
case class CurveSegment(val s: Segment,val x: Double, val y: Double,val heading:Double = 0) extends Segment{
  def heading(h: Double):Segment = new CurveSegment(s,x,y,h)
}
//A starting segment that move the path to the current point without drawing anything
//TODO: Create a way to simply jump to a point
case class PointSegment(val x: Double, val y: Double,val heading:Double = 0) extends Segment {
  def heading(h:Double):Segment = new PointSegment(x,y,h)
}

