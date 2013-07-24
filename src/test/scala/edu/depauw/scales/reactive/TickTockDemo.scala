package edu.depauw.scales.reactive

import edu.depauw.scales.graphics._

import javax.swing.{JFrame, WindowConstants}
import java.awt.geom.AffineTransform
import java.util.Calendar

object TickTockDemo extends App {

  val frame = new JFrame("Clock Demo")
  frame.setSize(600, 600)
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)

  type TimeState = (Double, Double, Int)
  val panel = ReactivePanel[TimeState](0, new AffineTransform, 
		  	    getTime, onRenderHandler,
		  	    onTickHandler, 12)
  
  pane.add(panel)
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  def onRenderHandler: TimeState => Graphic = {
    case time => 
      (
        Fill(Colors.CYAN, Polygon(50, 12)).center -&
        Line((0,0), (25*Math.sin(time._1*Math.PI/6), -25* Math.cos(time._1*Math.PI/6))) -&
        Line((0,0), (35*Math.sin(time._2*Math.PI/30), -35* Math.cos(time._2*Math.PI/30))) -&
        Line((0,0), (45*Math.sin(time._3*Math.PI/30), -45* Math.cos(time._3*Math.PI/30)))
      ).topLeft
  }
  
  def onTickHandler: TimeState => TimeState = {
	case time => getTime
  }
  
  def getTime: TimeState = {
    val c = Calendar.getInstance
    val s = c.get(Calendar.SECOND)
    val m = c.get(Calendar.MINUTE) + s/60.0
    val h = c.get(Calendar.HOUR) + m/60.0
    (h, m, s)
  }
}