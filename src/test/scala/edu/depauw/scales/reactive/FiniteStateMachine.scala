package edu.depauw.scales.reactive

import edu.depauw.scales.graphics._
import java.awt.event.MouseEvent
import edu.depauw.scales.ScalesApp
import java.awt.Color
import java.awt.geom.AffineTransform

object ShapeState extends Enumeration {
  type ShapeState = (Shape, Color)
  val RED_CIRCLE = (Circle(100), Colors.RED)
  val BLUE_CIRCLE = (Circle(100), Colors.BLUE)
  val RED_SQUARE = (Square(200), Colors.RED)
  val BLUE_SQUARE = (Square(200), Colors.BLUE)
}
import ShapeState._

object FiniteStateMachine extends ScalesApp(600,600) {
  
  // list of button names
  val btnNames = List("red", "blue", "circle", "square")
  
  // create the graphics for the buttons
  val buttons =
    btnNames.foldLeft(Phantom: Graphic)((c,name) => c -|| createButton(name).padLeft(40)).topLeft
  			
  // a map from ShapeStates to state names
  val stateToName = Map((ShapeState.RED_CIRCLE , "Red Circle" ),
		  				(ShapeState.BLUE_CIRCLE, "Blue Circle"),
		  				(ShapeState.RED_SQUARE , "Red Square" ),
		  				(ShapeState.BLUE_SQUARE, "Blue Square"))
  
  /* ------------------------ State Circles ----------------------- */
		  				
  val circle = scaleBounds(Circle(50), Math.sqrt(0.5))
  val c1 = Name("Red Circle" , circle) -& Text("Red Circle" ).centerAt(circle.c)
  val c2 = Name("Blue Circle", circle) -& Text("Blue Circle").centerAt(circle.c) -+ (200,0)
  val c3 = Name("Red Square" , circle) -& Text("Red Square" ).centerAt(circle.c) -+ (0,200)
  val c4 = Name("Blue Square", circle) -& Text("Blue Square").centerAt(circle.c) -+ (200,200)
  
  /* -------------------- Curves Between States ------------------- */
  
  val hCurve = ControlledBezierPath(c1.tr,(c1.tr._1+20,c1.tr._2-20),(c2.tl._1-20,c2.tl._2-20),c2.tl)-&
  				ControlledBezierPath(c1.br,(c1.br._1+20,c1.br._2+20),(c2.bl._1-20,c2.bl._2+20),c2.bl)
  val hCurves = hCurve-&Translate(0,200,hCurve)
  val vCurve = ControlledBezierPath(c1.bl,(c1.bl._1-20,c1.bl._2+20),(c3.tl._1-20,c3.tl._2-20),c3.tl)-&
  				ControlledBezierPath(c1.br,(c1.br._1+20,c1.br._2+20),(c3.tr._1+20,c3.tr._2-20),c3.tr)
  val vCurves = vCurve-&Translate(200,0,vCurve)
  
  /* ----------------------------- FSM ---------------------------- */
  val machine = (c1 -& c2 -& c3 -& c4 -& hCurves -& vCurves).topLeft
  
  // the composite background of buttons and machine
  val background = buttons -^ machine.pad(50)
  
  // create reactive panel
  val panel = ReactivePanel(0, new AffineTransform,
		  		ShapeState.RED_CIRCLE, onRenderHandler, onMouseEventHandler)
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* ================================= METHODS =================================== */
  
  /**
   * Method to create a named button with a corresponding label
   */
  def createButton(name: String): Graphic = {
    Name(name, Square(100).center) -&
    Text(name.capitalize, FontSize(28)).center
  }
  
  /**
   * Utility method to scale the bounds of a graphic by a given factor.
   * This is used in the demo in order to ensure that the points on the
   * bounding box correspond with actual points on the circle.
   */
  def scaleBounds(g:Graphic, scale: Double) = {
    val w = g.bounds.getWidth
    val h = g.bounds.getHeight
    g.changeBounds(w * scale, h * scale, w * (1-scale) / 2, h * (1-scale) / 2)
  }
  
  /**
   * Method to fill any sub-graphics with a given name with yellow.
   */
  def fillWithName(g: Graphic, name: String): Graphic = {
    g.withName(name).map(Fill(RGBA(255,255,0,60),_)).foldLeft(g)(_ -& _)
  }
  
  /**
   * Method to generate a graphic based on the current shape state
   */
  def onRenderHandler: ShapeState => Graphic = {
    case state @ (shape, color) => 
      fillWithName(background, stateToName(state)) -& 
      (Fill(color, shape) -@ (360, 200))
  }
  
  /**
   * Method to evolve from a previous ShapeState to a subsequent one based on a given MouseEvent
   */
  def onMouseEventHandler: (MouseEvent, ShapeState) => ShapeState = {
	case (e, (shape, color)) => 
	  if (e.getID == MouseEvent.MOUSE_CLICKED) {
	    
	    // check if the click hit one of the buttons
		btnNames.find(background.withName(_).head.shape.contains(e.getX, e.getY)) match {
		  
		  // if it didn't, just keep the same state
		  case None => (shape, color)
		  
		  // if it did, update the state accordingly
		  case Some(name) => name match {
		    case "red" => (shape, Colors.RED)
		    case "blue" => (shape, Colors.BLUE)
		    case "circle" => (Circle(100), color)
		    case "square" => (Square(200), color)
		  }
		}
	  } // if not a click, state is unchanged
	  else (shape, color)
  }
}
