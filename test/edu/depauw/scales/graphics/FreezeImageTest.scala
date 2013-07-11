package edu.depauw.scales.graphics

import java.awt.{Color, Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object FreezeImageTest extends App {
  val frame = new JFrame("Freeze Test")
  frame.setSize(800,600)
  
  val filename = System.getProperty("user.dir") + "/resources/warningsign.jpg"
   val g1 = Name("circle", Circle(20))
  val g3 = g1.freeze
  
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = g3 ||| g1
  				  
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
