package edu.depauw.scales.graphics

import java.awt.geom.{Ellipse2D,Rectangle2D,GeneralPath,Line2D,RoundRectangle2D,Point2D => Point}

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
  def apply(points : (Double, Double)*): Shape = {
    val poly : GeneralPath = new GeneralPath()
    
    val first = points.head
    poly.moveTo(first._1, first._2)
    for ((x, y) <- points.drop(1)) {
      poly.lineTo(x, y)
    }
    poly.closePath()
    
    Shape(poly)
  }
  
  def apply(radius: Double, sides: Int): Shape = {
    apply((1 to sides).map({
      i => (radius*Math.cos(2*Math.PI*i/sides), radius*Math.sin(2*Math.PI*i/sides))
    }): _*)
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
 * IF YOU USE THIS VERY BAD THINGS WILL HAPPEN. DONT USE THIS UNTIL IT IS READY
 */
object Path {
	def apply(segment: Segment): Graphic = {
	  val path:GeneralPath = new GeneralPath()
	  walkPath(makeSegList(segment))
	  def walkPath(segList: List[Segment]):Unit = segList match {
	    case oneAhead::twoAhead::rest => 
	      aux(oneAhead,twoAhead)
	      println("Just finished (" + oneAhead.x + ","+")")
	      walkPath(twoAhead::rest)
	    case oneMore::Nil => aux(oneMore,new PointSegment(0,0))
	    case _ => sys.error("An unexpected error has occured")
	  }
	  
	  def aux(oneAhead:Segment,twoAhead:Segment) = oneAhead match{
	    case s:PointSegment =>
	      path.moveTo(s.x,s.y)
	    case s:LineSegment =>
	      path.lineTo(s.x,s.y)
	    case s:CurveSegment =>
	      val here = path.getCurrentPoint()
	      val there = new Point.Double(twoAhead.x,twoAhead.y)
	      val dist: Double = here.distance(s.x, s.y)/3.0
	      val c1: (Double,Double) = {
	        val moveBy = (math.cos(s.heading)*dist,math.sin(s.heading)*dist)
	        (here.getX()-moveBy._1,here.getY()-moveBy._2)
	      }
	      val c2: (Double,Double) = {
	        val moveBy = (math.cos((twoAhead.heading+math.Pi)%(2*math.Pi))*dist,
	          math.sin((twoAhead.heading+math.Pi)%(2*math.Pi))*dist)
	          (there.getX()+moveBy._1,there.getY()+moveBy._2)
	      }
	      path.curveTo(c1._1,c1._2,c2._1,c2._2,s.x,s.y)
	  }
	  Shape(path)
	}
	def makeSegList(seg: Segment): List[Segment] = seg match{
	    case s: PointSegment => List(s)
	    case s: LineSegment => makeSegList(s.s):::List(s)
	    case s: CurveSegment => makeSegList(s.s):::List(s)
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
  def apply(p1: (Double,Double),p2:(Double,Double)): Graphic= apply(p1._1,p1._2,p2._1,p2._2)
}

object RoundRectangle {
  def apply(x: Double, y: Double, w: Double, h: Double, arcw: Double, arch: Double) =
    Shape(new RoundRectangle2D.Double(x,y,w,h,arcw,arch))
}
//TODO: , arcs, other curves, areas?

