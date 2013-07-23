package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints, Font => jFont, Color}
import javax.swing.{JFrame, JPanel, WindowConstants}


object FaceStick extends App {
  val frame = new JFrame("Scene")
  frame.setSize(600, 400)
  
  val image =  System.getProperty("user.dir") + "/resources/rudra.jpg"
  
 
  val me : Graphic =  /*Clip(Ellipse(0,0, 10,30),*/Image(image, 25,25,50,50)
  val face = Clip(Ellipse(0,0, 28,35),me.moveTo(-9,0))
  val body = StraightPath((0,0),(10,10),(10,0),(10,10),(20,0), (10,10), (10,30),(0,40),(10,30),(20,40))
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = face -^ Translate(5,0,body)
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
