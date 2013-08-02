package edu.depauw.scales
package graphics

import scala.language.implicitConversions

import Util._

/**
 * contains the curveTo and lineTo methods.
 * Segment uses a data structure where the previous segment is stored as a value
 * in the current segment, allowing the convenient .lineTo and .curveTo language
 * Read Path's documentation for more information on how to use Segments practially
 */
trait Segment {
	val x, y: Double
	val heading: Angle
	
	def lineTo(x: Double, y: Double): Segment = new LineSegment(this, x, y)
	def curveTo(x: Double, y: Double): Segment = new CurveSegment(this, x, y)
	def heading(h: Angle): Segment
}

/**
 * A segment that draws a straight line to the given point from the previous point
 */
case class LineSegment(s: Segment, x: Double, y: Double, heading: Angle = 0 rad) extends Segment {
  def heading(h: Angle): Segment = LineSegment(s, x, y, h)
}

/**
 * A segment that draws a bezier curve to the next point from the previous point
 * using both its heading and the heading of the following segment
 */
case class CurveSegment(s: Segment, x: Double, y: Double, heading: Angle = 0 rad) extends Segment{
  def heading(h: Angle): Segment = CurveSegment(s, x, y, h)
}

/**
 * A starting segment that move the path to the current point without drawing anything
 */
//TODO: Create a way to simply jump to a point
case class PointSegment(x: Double, y: Double, heading: Angle = 0 rad) extends Segment {
  def heading(h: Angle): Segment = PointSegment(x, y, h)
}

