package edu.depauw.scales.reactive

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._

import java.awt.geom.AffineTransform
import java.awt.event.MouseEvent

object StickFollow extends ScalesApp(800,600,RenderMode.SCALE_TO_FIT, "Reactive Rudra") {
  
  // specify image url
  val imageURL =  System.getProperty("user.dir") + "/resources/rudra.jpg"
  
  // add image as a graphic
  val me: Graphic = Image(imageURL, 50, 50)
  
  // clip image with an ellipse to use only the face
  val face = Clip(Ellipse(9, 0, 28, 35), me)
  
  // draw a stick figure body
  val body = StraightPath((0,0),(10,10),(10,0),(10,10),(20,0),(10,10),(10,30),(0,40),(10,30),(20,40))
  
  // place the face above the body
  val figure = face.center -^ body.center
  
  // type for the Reactive Panel state
  type PositionInfo = (Double, Double)
  
  // initial state puts rudra in the center
  val initState: PositionInfo = (50,50)
  
  // create reactive panel which reacts to MouseMotionEvents
  val panel = ReactivePanel[PositionInfo](0, new AffineTransform, 
		  	    initState, onRenderHandler, onMouseMotionEvent = Some(onMouseMotionEventHandler))
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* =========================== METHODS =================================== */
  
  /**
   * Method to convert position info to a graphic with the figure centered at that position
   */
  def onRenderHandler: PositionInfo => Graphic = {
    case (x, y) => figure centerAt (x, y)
  }
  
  /**
   * Method to convert a MouseEvent to a PositionInfo instance
   */
  def onMouseMotionEventHandler: (MouseEvent, PositionInfo) => PositionInfo = {
	case (e, prev) => (100 * e.getX / width, 100 * e.getY / height)
  }
}
