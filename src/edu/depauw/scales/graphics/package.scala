package edu.depauw.scales

package object graphics {
	implicit class Segment(val p: (Double, Double)) {
		def getHeading: Double = this match{
		  case s: LineSegment => s.heading
		  case s: CurveSegment => s.heading
		  case s: PointSegment => s.heading
		}
		def heading(h: Double): Segment = this match{
		  case s: LineSegment => new LineSegment(s.x,s.y,h)
		  case s: CurveSegment => new CurveSegment(s.x,s.y,h)
		  case s: PointSegment => new PointSegment(s.x,s.y,h)
		}
	}
}