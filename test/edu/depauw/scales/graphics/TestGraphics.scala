package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestGraphics extends App {
  val frame = new JFrame("Graphics Test")
  frame.setSize(600, 400)
  
  val g = Line(25, 25, 75, 75)
  val g2 = Fill(Colors.RED, Polygon((10, 10), (22, 80), (25, 25), (22, 10)))
  val front = new java.awt.Font("Serif", java.awt.Font.BOLD|java.awt.Font.ITALIC, 8)
  val g3 = Translate(10, 10, Text("Hello World!", front))
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = g | g2 | g3
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
