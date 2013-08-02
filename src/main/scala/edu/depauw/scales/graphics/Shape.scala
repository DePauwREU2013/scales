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
 * Path takes a segment and returns a Graphic.
 * 
 * Creating the segment:
 * To start creating a segment while using a ScalesApp, begin be defining a single point
 * by giving a tuple of doubles, like (5.0,7.0). If you are not using a ScalesApp
 * you will need to start be creating a new PointSegment using new PointSegment(x,y)
 * 
 * 
 * Adding more to the segment:
 * Once you have the initial point, you can use the .lineTo and .curveTo methods to
 * have the path move to the next point. It should look something like
 * new PointSegment(x1,y1).lineTo(x2,y2).curveTo(x3,y3)
 * 
 * Headings:
 * Each segment stores a heading that is used in the curveTo segments. A heading defines
 * which direction the path will be going at the point that segment STARTS at,
 * not where it ends. In order to have curveTo work correctly, you will need to have
 * a heading at the first curveTo segment AND at the next segment, or you'll get 
 * unexpected curving behavior. To add a heading to a segment use the .heading(h) method
 * like so, remembering that headings are always given in radians:  
 * (x1,y1).lineTo(x2.y2).curveTo(x3,y3).heading(3*math.Pi/2).lineTo(x4,y4).heading(math.Pi)
 * 
 * to make a segment into a math, just pass the segment to Path in the apply method.
 */
object Path {
	def apply(segment: Segment): Graphic = {
	  val path: Path2D = new Path2D.Double
	  
	  walkPath(makeSegList(segment))
	  
	  def walkPath(segList: List[Segment]): Unit = segList match {
	    case oneAhead :: twoAhead :: rest => 
	      aux(oneAhead, twoAhead)
	      walkPath(twoAhead :: rest)
	    case oneMore :: Nil => aux(oneMore, PointSegment(oneMore.x, oneMore.y, oneMore.heading))
	    case _ => {}
	  }
	  
	  // TODO are the headings off by one here?
	  def aux(oneAhead: Segment, twoAhead: Segment): Unit = oneAhead match {
	    case PointSegment(x, y, _) =>
	      path.moveTo(x, y)
	    case LineSegment(_, x, y, _) =>
	      path.lineTo(x, y)
	    case CurveSegment(_, x, y, heading) =>
	      val here = path.getCurrentPoint()
	      val there = new Point.Double(x, y)
	      val dist = here.distance(there) / 3
	      val (c1x, c1y) = {
	        val (dx, dy) = (math.cos(heading.inRadians)*dist, -math.sin(heading.inRadians)*dist)
	        (here.getX + dx, here.getY + dy)
	      }
	      val (c2x, c2y) = {
	        val (dx, dy) = (-math.cos(twoAhead.heading.inRadians + math.Pi)*dist,
	          math.sin(twoAhead.heading.inRadians + math.Pi)*dist)
	        (there.getX - dx, there.getY - dy)
	      }
	      path.curveTo(c1x, c1y, c2x, c2y, x, y)
	  }
	  Shape(path)
	}
	
	// TODO blech
	private def makeSegList(seg: Segment): List[Segment] = seg match {
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
//TODO arcs, other curves, areas?

