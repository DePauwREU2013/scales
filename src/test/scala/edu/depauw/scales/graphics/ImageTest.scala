package edu.depauw.scales.graphics

import java.awt.{Color, Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object ImageTest extends App {
  val frame = new JFrame("Image Test")
  frame.setSize(800, 600)
  
  val filename = System.getProperty("user.dir") + "/resources/warningsign.jpg"
  val g : Graphic = Rotate(0.3, 50, 50,
                           Scale(1.5, 0.75, 50, 50, Image(filename, 25, 25, 50, 50)))
  val g2 : Graphic = Bitmap(fn, 0, 0, 100, 100)
  def fn(x : Double, y : Double) : Color = RGBA(x, Math.min(x, y), y, 0.3)
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = g2 | g
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
