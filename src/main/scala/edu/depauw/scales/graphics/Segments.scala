package edu.depauw.scales
package graphics

import scala.language.implicitConversions

import Util._

trait Segment {
	val x, y: Double
	val heading: Angle
	
	def lineTo(x: Double, y: Double): Segment = new LineSegment(this, x, y)
	def curveTo(x: Double, y: Double): Segment = new CurveSegment(this, x, y)
	def heading(h: Angle): Segment
}

case class LineSegment(s: Segment, x: Double, y: Double, heading: Angle = 0 rad) extends Segment {
  def heading(h: Angle): Segment = LineSegment(s, x, y, h)
}

case class CurveSegment(s: Segment, x: Double, y: Double, heading: Angle = 0 rad) extends Segment{
  def heading(h: Angle): Segment = CurveSegment(s, x, y, h)
}

case class PointSegment(x: Double, y: Double, heading: Angle = 0 rad) extends Segment {
  def heading(h: Angle): Segment = PointSegment(x, y, h)
}

