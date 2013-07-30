package edu.depauw.scales.graphics

import scala.language.implicitConversions

trait Segment {
	val x,y,heading: Double
	def lineTo(x: Double,y:Double): Segment = new LineSegment(this,x,y)
	def curveTo(x:Double,y:Double):Segment = new CurveSegment(this,x,y)
	def heading(h:Double):Segment
}

object Segment {
//  implicit def point2Segment(p: (Double, Double)): Segment = new PointSegment(p._1,p._2)
}

case class LineSegment(val s: Segment,val x: Double,val y: Double,val heading:Double = 0) extends Segment{
  def heading(h:Double):Segment = new LineSegment(s,x,y,h)
}

case class CurveSegment(val s: Segment,val x: Double, val y: Double,val heading:Double = 0) extends Segment{
  def heading(h: Double):Segment = new CurveSegment(s,x,y,h)
}

case class PointSegment(val x: Double, val y: Double,val heading:Double = 0) extends Segment {
  def heading(h:Double):Segment = new PointSegment(x,y,h)
}

