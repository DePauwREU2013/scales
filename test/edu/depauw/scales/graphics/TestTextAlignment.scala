package edu.depauw.scales.graphics

import javax.swing.JFrame
import javax.swing.WindowConstants

object TestTextAlignment extends App {
  val frame = new JFrame("Text Alignment Test")
  frame.setSize(600, 600)
  
  val t1 = Text("Hello")
  val t2 = Text("World")
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = Translate(10, 10, t1 ||| t2) -& Translate(30, 80, t1 -^ t2)
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}