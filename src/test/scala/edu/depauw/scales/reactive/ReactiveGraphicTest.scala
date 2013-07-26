package edu.depauw.scales.reactive

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._

import java.awt.event.MouseEvent
import java.awt.geom.AffineTransform

object ReactiveGraphicTest extends ScalesApp(600,600,RenderMode.SCALE_TO_FIT, "Reactive Graphic Test") {
  
  // a map between MouseEvent ID's and strings
  val idToString = Map((MouseEvent.MOUSE_ENTERED , "enter"),
		  			   (MouseEvent.MOUSE_EXITED  , "exit" ),
		  			   (MouseEvent.MOUSE_PRESSED , "down" ),
		  			   (MouseEvent.MOUSE_RELEASED, "up"   ),
		  			   (MouseEvent.MOUSE_CLICKED , "click"))

  // create a background of checkboxes with labels; separate them vertically
  val background = 
    idToString.values.map(stringToCheckboxGraphic).foldLeft(Phantom -@ (10, 10))(vSpace(5))
  
  // type of state to be used in ReactivePanel
  type MouseInfo = Tuple2[String,Tuple2[Int,Int]]
  
  // initial state is basically blank
  val initState: MouseInfo = ("",(0,0))
  
  // create the reactive panel
  val panel = ReactivePanel[MouseInfo](0, new AffineTransform, 
		  	    initState, onRenderHandler, onMouseEventHandler)
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* ================================ METHODS =============================== */
  
  /**
   * Method to convert a mouse event string id to a graphic with 
   * a named checkbox and a label
   */
  def stringToCheckboxGraphic: String => Graphic = 
    name => Name(name, Square(10).middleRight) -& Translate(5, 0, Text("Mouse " + name).middleLeft)
  
  /**
   *  Composites two graphics, the second being left-aligned and vertically separated by `dy`
   *  
   *  @param dy Double amount of vertically separate the graphics when composited
   */ 
  def vSpace(dy: Double): (Graphic, Graphic) => Graphic = {
    case (composite, g) => composite -& (g -@ (composite.bl._1, composite.bl._2 + dy))
  } 
  
  /**
   * Method to fill any graphic matching a given name.
   * @param g Graphic the graphic to search for named sub-graphics within
   * @param name String the name to query for
   * @return a version of `g` with all matching sub-graphics filled with RED
   */
  def fillWithName(g: Graphic, name: String): Graphic = {
    g.withName(name).map(Fill(Colors.RED,_)).foldLeft(g)(_ -& _)
  }
  
  /**
   * Method to generate a Graphic given a MouseInfo state.
   * Fills in the checkbox corresponding to the last MouseEvent
   * and updates the last position of the mouse.
   */
  def onRenderHandler: MouseInfo => Graphic = {
    case (eventName, pos) => 
      fillWithName(background, eventName) -& 
      (Text("last pos: " + pos.toString, FontSize(6)) -@ (10, 5))
  }
  
  /**
   * Extracts MouseInfo data from a MouseEvent
   */
  def onMouseEventHandler: (MouseEvent, MouseInfo) => MouseInfo = {
	case (e, ms) => (idToString(e.getID), (e.getX,e.getY))
  }
}
