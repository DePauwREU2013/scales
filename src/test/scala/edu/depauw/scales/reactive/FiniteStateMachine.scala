package edu.depauw.scales.reactive

import edu.depauw.scales.graphics._
import java.awt.{Color, Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}
import java.awt.geom.AffineTransform
import java.awt.event.MouseEvent

object ShapeState extends Enumeration {
  type ShapeState = Tuple2[Shape, Color]
  val RED_CIRCLE = (Circle(100), Colors.RED)
  val BLUE_CIRCLE = (Circle(100), Colors.BLUE)
  val RED_SQUARE = (Square(200), Colors.RED)
  val BLUE_SQUARE = (Square(200), Colors.BLUE)
}
import ShapeState._

object TestAlign extends App{
  val frame = new JFrame("Alignment Test")
  frame.setSize(600, 600)
  
  val sq = Square(100).center
  val buttons = List(
      Name("redButton", sq) -& Text("Red", FontSize(28)).center,
      Name("blueButton", sq) -& Text("Blue", FontSize(28)).center,
      Name("circleButton", sq) -& Text("Circle", FontSize(28)).center,
      Name("squareButton", sq) -& Text("Square", FontSize(28)).center
    ).foldLeft(Phantom: Graphic)((c,g)=> c -|| g.padLeft(40)).topLeft
  val btnNames = List("redButton", "blueButton", "circleButton", "squareButton")
  				
  val circle = scaleBounds(Circle(50), Math.sqrt(0.5)).center
  val c1 = Name("Red Circle", circle) -& Text("Red Circle").center
  val c2 = Translate(200,0,Name("Blue Circle", circle) -& Text("Blue Circle").center)
  val c3 = Translate(0,200,Name("Red Square", circle) -& Text("Red Square").center)
  val c4 = Translate(200,200,Name("Blue Square", circle) -& Text("Blue Square").center)
  
  val stateToName = Map(
      (ShapeState.RED_CIRCLE, "Red Circle"),
      (ShapeState.BLUE_CIRCLE, "Blue Circle"),
      (ShapeState.RED_SQUARE, "Red Square"),
      (ShapeState.BLUE_SQUARE, "Blue Square")
    )
  
  val hCurve = ControlledBezierPath(c1.tr,(c1.tr._1+20,c1.tr._2-20),(c2.ul._1-20,c2.ul._2-20),c2.ul)-&
  				ControlledBezierPath(c1.br,(c1.br._1+20,c1.br._2+20),(c2.bl._1-20,c2.bl._2+20),c2.bl)
  val hCurves = hCurve-&Translate(0,200,hCurve)
  val vCurve = ControlledBezierPath(c1.bl,(c1.bl._1-20,c1.bl._2+20),(c3.ul._1-20,c3.ul._2-20),c3.ul)-&
  				ControlledBezierPath(c1.br,(c1.br._1+20,c1.br._2+20),(c3.tr._1+20,c3.tr._2-20),c3.tr)
  val vCurves = vCurve-&Translate(200,0,vCurve)
  val machine = (c1-&c2-&c3-&c4-&hCurves-&vCurves).topLeft
  
  val background = buttons -^ machine.pad(50)
  
  val panel = ReactivePanel[ShapeState](0, new AffineTransform,
		  		ShapeState.RED_CIRCLE, onRenderHandler, onMouseEventHandler)
  panel.graphic = background
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  def scaleBounds(g:Graphic, scale: Double) = {
    val w = g.bounds.getWidth
    val h = g.bounds.getHeight
    g.changeBounds(w * scale, h * scale, w * (1-scale) / 2, h * (1-scale) / 2)
  }
  
  def fillWithName(g: Graphic, name: String): Graphic = {
    g.withName(name).map(Fill(RGBA(255,255,0,60),_)).foldLeft(g)(_ -& _)
  }
  
  def onRenderHandler: ShapeState => Graphic = {
    case state @ (shape, color) => 
      fillWithName(background, stateToName(state)) -& 
      (Fill(color, shape) -@ (360, 200))
  }
  
  def onMouseEventHandler: (MouseEvent, ShapeState) => ShapeState = {
	case (e, (shape, color)) => {
	  if (e.getID == MouseEvent.MOUSE_CLICKED) {
		btnNames.find(background.withName(_).head.shape.contains(e.getX, e.getY)) match {
		  case None => (shape, color)
		  case Some(name) => name match {
		    case "redButton" => (shape, Colors.RED)
		    case "blueButton" => (shape, Colors.BLUE)
		    case "circleButton" => (Circle(100), color)
		    case "squareButton" => (Square(200), color)
		  }
		}
	  } else (shape, color)
	}
  }
}
