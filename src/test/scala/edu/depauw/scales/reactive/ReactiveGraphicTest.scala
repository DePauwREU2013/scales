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
  
  // a map between MouseEvent ID's and strings
  val idToString = Map(
      (MouseEvent.MOUSE_ENTERED, "enter"),
      (MouseEvent.MOUSE_EXITED, "exit"),
      (MouseEvent.MOUSE_PRESSED, "down"),
      (MouseEvent.MOUSE_RELEASED, "up"),
      (MouseEvent.MOUSE_CLICKED, "click")
    )

  // create a background of checkboxes with labels; separate them vertically
  val background = 
    idToString.values.map(stringToCheckboxGraphic).foldLeft(Phantom -@ (10, 10))(vSpace(5))
  
  type MouseInfo = Tuple2[String,Tuple2[Int,Int]]
  val initState: MouseInfo = ("",(0,0))
  
  val panel = ReactivePanel[MouseInfo](0, new AffineTransform, 
		  	    initState, onRenderHandler, onMouseEventHandler)
  
  pane.add(panel)
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  def stringToCheckboxGraphic: String => Graphic = 
    name => Name(name, Square(10).middleRight) -& Translate(5, 0, Text("Mouse " + name).middleLeft)
  
  // composites two graphics, the second being left-aligned and vertically separated by `dy`
  def vSpace(dy: Double): (Graphic, Graphic) => Graphic = {
    case (composite, g) => composite -& (g -@ (composite.bl._1, composite.bl._2 + dy))
  } 
  
  def fillWithName(g: Graphic, name: String): Graphic = {
    g.withName(name).map(Fill(Colors.RED,_)).foldLeft(g)(_ -& _)
  }
  
  def onRenderHandler: MouseInfo => Graphic = {
    case (eventName, pos) => 
      fillWithName(background, eventName) -& 
      (Text("last pos: " + pos.toString, FontSize(6)) -@ (10, 5))
  }
  
  def onMouseEventHandler: (MouseEvent, MouseInfo) => MouseInfo = {
	case (e, ms) => (idToString(e.getID), (e.getX,e.getY))
  }
}
