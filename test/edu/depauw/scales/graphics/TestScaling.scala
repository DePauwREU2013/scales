package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestScaling extends App {
  val frame = new JFrame("Alignment Test")
  frame.setSize(600, 600)
  
  val g1 = Circle(50)
  val g2 = Fill(Colors.RED, Circle(5))
  val g3 = Fill(Colors.BLUE, Square(10))
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = Scale(0.1, 0.1, g1) -& Scale(10, 10, g2)
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}