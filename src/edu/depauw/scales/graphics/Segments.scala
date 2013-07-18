package edu.depauw.scales.graphics

trait Segment {
	val x,y,heading: Double
	def lineTo(x: Double,y:Double): Segment = new LineSegment(this,x,y)
	def curveTo(x:Double,y:Double):Segment = new CurveSegment(this,x,y)
	def heading(h:Double):Segment = this match {
	  case s: LineSegment => new LineSegment(s.s,s.x,s.y,h)
	  case s: CurveSegment => new CurveSegment(s.s,s.x,s.y,h)
	  case s: PointSegment => new PointSegment(s.x,s.y,h)
	}
}
case class LineSegment(val s: Segment,val x: Double,val y: Double,val heading:Double = 0) extends Segment

case class CurveSegment(val s: Segment,val x: Double, val y: Double,val heading:Double = 0) extends Segment

case class PointSegment(val x: Double, val y: Double,val heading:Double = 0) extends Segment
