package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestAlign extends App{
  val frame = new JFrame("Alignment Test")
  frame.setSize(600, 600)
  
  val g1 = Circle(20)
  val g2 = Fill(Colors.RED, Circle(10))
  val g3 = Fill(Colors.BLUE, Square(10))
  val g4 = Path(new PointSegment(0,0).lineTo(25, 25).heading(3*math.Pi/2).curveTo(50,50))
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = g4
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
