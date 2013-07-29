package edu.depauw.scales.reactive

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._

import java.util.Calendar
import java.lang.Math.{cos, sin, PI}
import java.awt.geom.AffineTransform

object TickTockDemo extends ScalesApp(600,600,RenderMode.SCALE_TO_FIT, "Clock Demo") {
  
  // the type of the state to be used in the ReactivePanel
  type TimeState = (Double, Double, Int)
  
  // create a reactive panel which ticks
  val panel = ReactivePanel[TimeState](0, new AffineTransform, 
		  	    getTime, onRenderHandler,
		  	    onTickHandler)
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  // clock parts
  lazy val clock = Polygon(50, 12).center -* Colors.CYAN
  
  def hourHand(hour: Double = 0): Graphic =
    Line((0,0), (25*sin(hour*PI/6), -25*cos(hour*PI/6))) -~ 2
    
  def minuteHand(min: Double = 0): Graphic =
    Line((0,0), (35*sin(min*PI/30), -35*cos(min*PI/30))) -~ 2
    
  def secondHand(sec: Int = 0): Graphic =
    Line((0,0), (45*sin(sec*PI/30), -45*cos(sec*PI/30))) -~ (1, Colors.RED)
  
  /**
   * Method to convert a TimeState to a renderable clock graphic
   */
  def onRenderHandler: TimeState => Graphic = {
    case time => 
      ( 
        clock -&
        hourHand(time._1) -&
        minuteHand(time._2) -&
        secondHand(time._3)
      ).topLeft
  }
  
  /**
   * When the animation cycles, check for the current time
   */
  def onTickHandler: TimeState => TimeState = {	case time => getTime }
  
  /**
   * Retrieves a TimeState instance from the System time
   */
  def getTime: TimeState = {
    val c = Calendar.getInstance
    val s = c.get(Calendar.SECOND)
    val m = c.get(Calendar.MINUTE) + s/60.0
    val h = c.get(Calendar.HOUR) + m/60.0
    (h, m, s)
  }
}