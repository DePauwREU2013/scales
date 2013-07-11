package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints, Font => jFont, Color}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestGraphics extends App {
  val frame = new JFrame("Graphics Test")
  frame.setSize(600, 400)
  
  val g = Line(25, 25, 75, 75)
  val g2 = Fill(Colors.RED, Polygon((10, 10), (22, 80), (25, 25), (22, 10)))
  
  val serifFont = new Font("Serif", FontStyleType.BOLD | FontStyleType.ITALIC)
  val g3 = Translate(10, 10, Text("Hello World!", serifFont))
  
  def fun(x: Double, y: Double): Color = RGBA(x*y, (1-x)*y, x*(1-y), (x+1)*(y+1)/4)
  val g4 = Bitmap(fun, 10, 10, 80, 80)
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = g | g2 | g3 | g4
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
