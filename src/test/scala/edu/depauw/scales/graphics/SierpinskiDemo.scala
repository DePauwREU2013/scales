package edu.depauw.scales.graphics

import javax.swing.JFrame
import javax.swing.WindowConstants

object SierpinskiDemo extends App {
  val frame = new JFrame("Freeze Test")
  frame.setSize(800,600)
  
  def sierpinskify(g: Graphic): Graphic = {
    val f = Freeze(Scale(0.5, g))
    Translate(f.bounds.getCenterX, 0, f) -^ (f ||| f)
  }
  
  def sierpinski(g: Graphic): Stream[Graphic] = Stream.iterate(g)(sierpinskify)
    
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = sierpinski(Fill(Colors.BLUE, Square(100)))(7)
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}