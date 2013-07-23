package edu.depauw.scales.graphics

import java.awt.{Color, Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestSmash extends App {
  val frame = new JFrame("Smash Test")
  frame.setSize(800, 600)
  
  
  val g =Fill(Colors.RED, Circle(15))
  val g2 = Fill (Colors.BLUE,Rectangle(0,0, 20,10))
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = ((g.hSmash -^ g2) |||(g.vSmash ||| g2))-^ ((g-^g2) |||( g ||| g2))
  
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
