package edu.depauw.scales.graphics

class LineSegment(val dest:(Double,Double),val heading:Double = 0) extends Segment(dest._1,dest._2)

class CurveSegment(val dest:(Double,Double),val heading:Double = 0) extends Segment(dest._1,dest._2)

class PointSegment(val dest:(Double,Double),val heading:Double = 0) extends Segment(dest._1,dest._2)