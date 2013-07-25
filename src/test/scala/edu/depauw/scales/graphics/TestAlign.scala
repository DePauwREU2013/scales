package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestAlign extends App{
  val frame = new JFrame("Fibonacci test")
  frame.setSize(600, 600)
  
  val spiral = Scale(5,4.5,Path(new PointSegment(30,60).curveTo(20,50).heading(math.Pi).
      curveTo(30,40).heading(math.Pi/2).curveTo(40,60).heading(0).curveTo(20,80).heading(3*math.Pi/2).
      curveTo(0,40).heading(math.Pi).curveTo(40,0).heading(math.Pi/2).curveTo(100,80).heading(0).
      lineTo(100,80).heading(3*math.Pi/2)))
  val fibRect = Scale(5,4.5,(Square(40)-^(Rectangle(0.0,0.0,20.0,40.0)|||(((Square(10)-^Square(10))|||Rectangle(0,0,10,20))-^Square(20))))|||Rectangle(0,0,60,80))
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = spiral-&fibRect
  
  val pane = new ScalesPanel(RenderMode.DEFAULT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
