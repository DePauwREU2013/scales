package edu.depauw.scales.reactive

import javax.swing.{JFrame, WindowConstants}
import java.awt.event.MouseEvent
import reactive.Observing
import edu.depauw.scales.graphics._
import java.awt.geom.AffineTransform
import scala.collection.immutable.Map

object StickFollow extends App {
  val frame = new JFrame("Reactive Rudra")
  frame.setSize(800, 600)
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)

  val image =  System.getProperty("user.dir") + "/resources/rudra.jpg"
  
  val me : Graphic = Image(image, 25,25,50,50)
  val face = Clip(Ellipse(0,0, 28,35),me.moveTo(-9,0))
  val body = StraightPath((0,0),(10,10),(10,0),(10,10),(20,0), (10,10), (10,30),(0,40),(10,30),(20,40))
  val figure = face -^ Translate(5,0,body)
    
  type PositionInfo = Tuple2[Tuple2[Double,Double],Tuple2[Double,Double]]
  val initState: PositionInfo = ((50,50),(50,50))
  
  val panel = ReactivePanel[PositionInfo](0, new AffineTransform, 
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
  
  def onRenderHandler: PositionInfo => Graphic = {
    case (last, (x, y)) => figure -@ (x - figure.bounds.getWidth/2,y - figure.bounds.getHeight/2)
  }
  
  def onMouseEventHandler: (MouseEvent, PositionInfo) => PositionInfo = {
	case (e, prev) => 
	  if (e.getID == MouseEvent.MOUSE_CLICKED) {
	    (prev._1, (100 * e.getX / pane.getWidth, 100 * e.getY / pane.getHeight))
	  } else prev
  }
}
