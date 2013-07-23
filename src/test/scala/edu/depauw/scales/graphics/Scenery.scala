package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints, Font => jFont, Color}
import javax.swing.{JFrame, JPanel, WindowConstants}


object Scenery extends App {
  val frame = new JFrame("Scene")
  frame.setSize(600, 400)
  val serifFont = new Font("Serif", FontStyleType.ITALIC)
  
 
  val sun = Fill(Colors.YELLOW,Circle(15))
  val mountain = Fill(Colors.LIGHT_GRAY, Polygon((0,10),(25,10),(12.5,-40)))
  val range = mountain ||| mountain ||| mountain ||| mountain
  val water = Fill(Colors.BLUE, Rectangle(0,0,100,100))
  
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = sun -^ range.moveTo(0, 40) -^ water.moveTo(0, 80)
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
