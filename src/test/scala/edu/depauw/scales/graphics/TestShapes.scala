package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints, Font => jFont, Color}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestShapes extends App {
  val frame = new JFrame("Graphics Test")
  frame.setSize(600, 400)
  val serifFont = new Font("Serif", FontStyleType.BOLD | FontStyleType.ITALIC)
  
 
  val g2 = (Circle(5) ||| Text("Circle", serifFont))
  val g3 = (Ellipse(0, 0, 5, 10) ||| Text("Ellipse", serifFont))
  val g4 = (Rectangle(0, 0, 5, 10) ||| Text("Rectangle", serifFont))
  val g5 = (Square(5) ||| Text("Square", serifFont))
  val g6 = (Polygon((0,0), (10,10), (15,20), (10,50), (5,15), (0,0)) ||| Text("Polygon", serifFont))
  
  
  
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic =  g2 -^ g3 -^ g4 -^ g5 -^ g6
  
  val pane = new ScalesPanel(RenderMode.DEFAULT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
