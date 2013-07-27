package edu.depauw.scales.graphics

import java.awt.geom.{Ellipse2D,Rectangle2D,Path2D,Line2D,RoundRectangle2D,Point2D => Point}

case class Shape(jShape : java.awt.Shape) extends Graphic {
  def render(gc : GraphicsContext) {
    gc.drawShape(jShape)
  }
  
  override lazy val bounds = jShape.getBounds2D
  
  override lazy val shape = jShape
  
  def withName(name: String) = Nil
}

object Circle {
  // (x, y) is the top left of the bounding box
  def apply(radius: Double, x: Double = 0, y: Double = 0) =
    Shape(new Ellipse2D.Double(x, y, 2 * radius, 2 * radius))
}

object Ellipse {
  // (x, y) is at the top left of the bounding box
  def apply(xDiam: Double, yDiam: Double, x: Double = 0, y: Double= 0) =
    Shape(new Ellipse2D.Double(x, y, xDiam, yDiam))
}

object Rectangle {
  // (x, y) is at the top left of the bounding box
  def apply(width: Double, height: Double, x: Double = 0, y: Double = 0) =
    Shape(new Rectangle2D.Double(x, y, width, height))
}

object Square {
  // (x, y) is at the top left of the bounding box
  def apply(width: Double, x: Double = 0, y: Double = 0) =
    Shape(new Rectangle2D.Double(x, y, width, width))
}

/*
 * A sequence of points connected by straight lines
 */
object StraightPath {
  def apply(points: (Double,Double)*) = {
    Shape(toPath2D(points: _*))
  }
  
  def toPath2D(points: (Double, Double)*): Path2D = {
    var path: Path2D = new Path2D.Double()
    
    points match {
      // handle the empty case
      case Seq() => path.moveTo(0,0)
      case _ => {
        path.moveTo(points.head._1, points.head._2)
        for ((x,y) <- points.tail) path.lineTo(x, y)
      }
    }
    path
  }
}

/*
 * A closed sequence of points connected by straight lines.
 */
object Polygon {
  def apply(points: (Double, Double)*): Graphic = {
    var poly = StraightPath.toPath2D(points: _*)
    poly.closePath()
    Shape(poly)
  }
  
  def apply(radius: Double, sides: Int): Graphic = {
    apply((1 to sides).map({
      i => (radius*Math.cos(2*Math.PI*i/sides), radius*Math.sin(2*Math.PI*i/sides))
    }): _*)
  }
}

/*
 * Sort of works. curveTo still causes some issues
 */
object Path {
	def apply(segment: Segment): Graphic = {
	  val path:Path2D = new Path2D.Double()
	  walkPath(makeSegList(segment))
	  def walkPath(segList: List[Segment]):Unit = segList match {
	    case oneAhead::twoAhead::rest => 
	      aux(oneAhead,twoAhead)
	      walkPath(twoAhead::rest)
	    case oneMore::Nil => aux(oneMore,new PointSegment(oneMore.x,oneMore.y).heading(oneMore.heading))
	    case _ => Blank()
	  }
	  
	  def aux(oneAhead:Segment,twoAhead:Segment) = oneAhead match{
	    case s:PointSegment =>
	      path.moveTo(s.x,s.y)
	    case s:LineSegment =>
	      path.lineTo(s.x,s.y)
	    case s:CurveSegment =>
	      val here = path.getCurrentPoint()
	      val there = new Point.Double(oneAhead.x,oneAhead.y)
	      val dist: Double = here.distance(there.x, there.y)/3.0
	      val c1: (Double,Double) = {
	        val moveBy = (math.cos(s.heading)*dist,-math.sin(s.heading)*dist)
	        (here.getX()+moveBy._1,here.getY()+moveBy._2)
	      }
	      val c2: (Double,Double) = {
	        val moveBy = (-math.cos((twoAhead.heading+math.Pi))*dist,
	          math.sin((twoAhead.heading+math.Pi))*dist)
	          (there.getX()-moveBy._1,there.getY()-moveBy._2)
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
    val path: Path2D = new Path2D.Double()
    val first = points.head
    path.moveTo(first._1,first._2)
    aux(points.drop(1).toList)
    def aux(points: List[(Double,Double)]): Unit = points match{
      case control1::control2::end::rest => 
        path.curveTo(control1._1, control1._2, control2._1, control2._2, end._1, end._2)
        aux(rest)
      case _ => {}
    }
    Shape(path)
  }
}

object Line {
  def apply(x1 : Double, y1 : Double, x2 : Double, y2 : Double): Graphic = 
    Shape(new Line2D.Double(x1, y1, x2, y2))
  
  def apply(p1: (Double,Double),p2:(Double,Double)): Graphic =
    apply(p1._1, p1._2, p2._1, p2._2)
}

object RoundRectangle {
  def apply(w: Double, h: Double, arcw: Double, arch: Double, x: Double = 0, y: Double = 0) =
    Shape(new RoundRectangle2D.Double(x, y, w, h, arcw, arch))
}
//TODO: , arcs, other curves, areas?

