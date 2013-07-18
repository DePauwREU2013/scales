package edu.depauw.scales.reactive

import javax.swing.{JFrame, WindowConstants}
import java.awt.event.MouseEvent
import reactive.Observing
import edu.depauw.scales.graphics._
import java.awt.geom.AffineTransform
import scala.collection.immutable.Map

object ReactiveGraphicTest extends App {
  val frame = new JFrame("Reactive Graphic Test")
  frame.setSize(600, 600)
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  //val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  
  val background: Graphic = {
    (Name("enter", Square(10).middleRight) -& Translate(5, 0, Text("Mouse Entered").middleLeft) -@ (10,15)) -&
    (Name("exit", Square(10).middleRight) -& Translate(5, 0, Text("Mouse Exited").middleLeft) -@ (10,30)) -&
    (Name("down", Square(10).middleRight) -& Translate(5, 0, Text("Mouse Pressed").middleLeft) -@ (10,45)) -&
    (Name("up", Square(10).middleRight) -& Translate(5, 0, Text("Mouse Released").middleLeft) -@ (10,60)) -&
    (Name("click", Square(10).middleRight) -& Translate(5, 0, Text("Mouse Clicked").middleLeft) -@ (10,75))
  }
  
  val idToString = Map(
      (MouseEvent.MOUSE_ENTERED, "enter"),
      (MouseEvent.MOUSE_EXITED, "exit"),
      (MouseEvent.MOUSE_PRESSED, "down"),
      (MouseEvent.MOUSE_RELEASED, "up"),
      (MouseEvent.MOUSE_CLICKED, "click")
      )
  /*
  panel.graphic = background
  
  for (e <- pane.mouseEventStream) {
    panel.graphic = fillWithName(background,
        e.getID match {
	      case MouseEvent.MOUSE_ENTERED =>  "enter"
	      case MouseEvent.MOUSE_EXITED => "exit"
	      case MouseEvent.MOUSE_PRESSED => "down"
	      case MouseEvent.MOUSE_RELEASED => "up"
	      case MouseEvent.MOUSE_CLICKED => "click"
	    }) -& (Text("last pos: " + (e.getPoint.x, e.getPoint.y).toString, FontSize(6)) -@ (10, 5))
    pane.repaint()
  }
  */
  
  type MouseState = Tuple2[String,Tuple2[Int,Int]]
  
  val initState: MouseState = ("",(0,0))
  
  def onRenderHandler: MouseState => Graphic = new Function1[MouseState, Graphic] {
    def apply(ms: MouseState) = ms match {
		  	    case (eventName, pos) => {
		  	      fillWithName(background, eventName) -&
		  	      (Text("last pos: " + pos.toString, FontSize(6)) -@ (10, 5))
		  	    }}
  }
  
  def onMouseEventHandler: Tuple2[MouseEvent, MouseState] => MouseState = 
    new Function1[Tuple2[MouseEvent, MouseState], MouseState] {
      def apply(inf: Tuple2[MouseEvent, MouseState]) = inf match {
        case (e, ms) => (idToString(e.getID), (e.getX,e.getY))
      }
  	}
  
  val panel = ReactivePanel[MouseState](0, new AffineTransform, 
		  	  state = initState, onRenderHandler, onMouseEventHandler)
		  	  
  pane.add(panel)
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  def fillWithName(g: Graphic, name: String): Graphic = {
    g.withName(name).map(Fill(Colors.RED,_)).foldLeft(g)(_ -& _)
  }
}
