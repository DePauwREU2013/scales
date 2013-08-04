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
	
	def moveTo(x: Double, y: Double): Segment = new MoveSegment(this, x, y)
	def lineTo(x: Double, y: Double): Segment = new LineSegment(this, x, y)
	def curveTo(x: Double, y: Double): Segment = new CurveSegment(this, x, y)
	def turnTo(h: Angle): Segment = new HeadingSegment(this, h)
	
	def moveBy(dx: Double, dy: Double): Segment = new MoveSegment(this, x+dx, y+dy)
	def lineBy(dx: Double, dy: Double): Segment = new LineSegment(this, x+dx, y+dy)
	def curveBy(dx: Double, dy: Double): Segment = new CurveSegment(this, x+dx, y+dy)
	def turnBy(dh: Angle): Segment = new TurningSegment(this, dh)
	
	def -@(x: Double, y: Double): Segment = moveTo(x, y)
	def --(x: Double, y: Double): Segment = lineTo(x, y)
	def -?(x: Double, y: Double): Segment = curveTo(x, y)
	def -%(h: Angle): Segment = turnTo(h)
	
	def +@(dx: Double, dy: Double): Segment = moveBy(dx, dy)
	def +-(dx: Double, dy: Double): Segment = lineBy(dx, dy)
	def +?(dx: Double, dy: Double): Segment = curveBy(dx, dy)
	def +%(dh: Angle): Segment = turnBy(dh)
}

/**
 * A starting segment that move the path to the current point without drawing anything
 */
case class PointSegment(x: Double, y: Double) extends Segment

/**
 * A segment that jumps to the given point from the previous point without drawing
 */
case class MoveSegment(s: Segment, x: Double, y: Double) extends Segment

/**
 * A segment that draws a straight line to the given point from the previous point
 */
case class LineSegment(s: Segment, x: Double, y: Double) extends Segment

/**
 * A segment that draws a bezier curve to the next point from the previous point
 * using both its heading and the heading of the following segment
 */
case class CurveSegment(s: Segment, x: Double, y: Double) extends Segment

/**
 * A segment that sets the direction (angle clockwise from x-axis) at the current point
 */
case class HeadingSegment(s: Segment, heading: Angle) extends Segment {
  val x = s.x
  val y = s.y
}

/**
 * A segment that modifies the direction at the current point by adding angle dh
 */
case class TurningSegment(s: Segment, dh: Angle) extends Segment {
  val x = s.x
  val y = s.y
}
