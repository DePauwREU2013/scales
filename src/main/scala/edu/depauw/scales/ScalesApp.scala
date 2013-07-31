package edu.depauw.scales

import edu.depauw.scales.graphics._
import edu.depauw.scales.graphics.RenderMode._

import javax.swing.{JFrame, WindowConstants}

import scala.language.implicitConversions

class ScalesApp(val width: Int = 640, val height: Int = 480,
    			val mode: RenderMode = RenderMode.DEFAULT,
    			val title: String = "Scales Application") extends App {
  
  val frame = new JFrame(title)
  frame.setSize(width, height)
  
  val scalesPanel = new ScalesPanel(mode)
  scalesPanel.setFocusable(true)
  
  frame.add(scalesPanel)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  def addPanel(panel: GraphicPanel): Unit = { 
    scalesPanel.add(panel)
    scalesPanel.repaint()
  }
  
  implicit def point2Segment(p: (Double, Double)): Segment = new PointSegment(p._1,p._2)
}
