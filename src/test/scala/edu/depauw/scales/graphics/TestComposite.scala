package edu.depauw.scales.graphics

import java.awt.{Color, Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestComposite extends App {
  val frame = new JFrame("Composite Test")
  frame.setSize(800, 600)
  
  
  val g =Fill(Colors.RED, Circle(15))
  val g2 = Fill (Colors.BLUE,Rectangle(0,0, 20,10))
  val g3 = Fill (Colors.GREEN,Polygon((0,0),(10,10),(20,0)))
  val g4 = Fill(Colors.YELLOW, Ellipse(0,0,10,15)) 
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = (g -^ g2) ||| (g3 -& g4)
  
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
