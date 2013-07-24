package edu.depauw.scales.graphics

import javax.swing.JFrame
import javax.swing.WindowConstants
import java.awt.geom.Point2D

object KochDemo extends App {
  val frame = new JFrame("Freeze Test")
  frame.setSize(800,600)
  
  def kochify(n:Int,p1:Point2D.Double,p2: Point2D.Double):Graphic = n match{
    case 0 => Line(p1.getX(),p2.getY(),p2.getX(),p2.getY())
    case _ if n > 0 =>
      val dx = p2.getX()-p1.getX()/3
      val dy = p2.getY()-p1.getY()/3
      val firstNew = new Point2D.Double(p1.getX()+dx,p1.getY()+dy)
      val secondnew = new Point2D.Double(p2.getX()-dx,p2.getY()-dy)
      Blank()
    case _ => sys.error("negative values for n not allowed")
  }

  def kochFlake(p1:Point2D.Double,p2:Point2D.Double):Graphic = kochify(3,p1,p2)
    
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  
  val pane = new ScalesPanel()
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}