package edu.depauw.scales.graphics

import javax.swing.JFrame
import javax.swing.WindowConstants

object TestTextAlignment extends App {
  val frame = new JFrame("Text Alignment Test")
  frame.setSize(600, 600)
  
  val t1 = Text("Hello")
  val t2 = Text("World")
  
  val t3 = Text("H") -|| Translate(0, 2, Text("2", FontSize(4))) -|| Text("O")
  val t4 = (Text("H").bottomRight -& Text("2", FontSize(4)).topLeft).topRight -& Text("O").topLeft
  
  val t5 = Circle(20).center -& t4.center
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = Translate(10, 10, t1 ||| t2) -&
  				  Translate(10, 80, t1 -^ t2) -&
  				  Translate(10, 40, t3) -&
  				  Translate(10, 60, t4) -&
  				  Translate(70, 60, t5)
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}