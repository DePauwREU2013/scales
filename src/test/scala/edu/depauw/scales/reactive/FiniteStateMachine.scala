package edu.depauw.scales.reactive

import edu.depauw.scales.graphics._
import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestAlign extends App{
  val frame = new JFrame("Alignment Test")
  frame.setSize(600, 600)
  
  val s1 = Square(50).center -& Text("Red").center
  val s2 = Square(50).center -& Text("Blue").center
  val s3 = Square(50).center -& Text("Circle").center
  val s4 = Square(50).center -& Text("Square").center
  val buttons = s1|||Phantom(s1)|||s2|||Phantom(s2)|||s3|||Phantom(s3)|||s4
  val c1 = Square(50).center -& Text("Red Circle").center
  val c2 = Translate(200,0,Square(50).center -& Text("Blue Circle").center)
  val c3 = Translate(0,200,Square(50).center -& Text("Red Square").center)
  val c4 = Translate(200,200,Square(50).center -& Text("Blue Square").center)
  val hCurve = ControlledBezierPath(c1.tr,(c1.tr._1+20,c1.tr._2-20),(c2.ul._1-20,c2.ul._2-20),c2.ul)-&
  				ControlledBezierPath(c1.br,(c1.br._1+20,c1.br._2+20),(c2.bl._1-20,c2.bl._2+20),c2.bl)
  val hCurves = hCurve-&Translate(0,200,hCurve)
  val vCurve = ControlledBezierPath(c1.bl,(c1.bl._1-20,c1.bl._2+20),(c3.ul._1-20,c3.ul._2-20),c3.ul)-&
  				ControlledBezierPath(c1.br,(c1.br._1+20,c1.br._2+20),(c3.tr._1+20,c3.tr._2-20),c3.tr)
  val vCurves = vCurve-&Translate(200,0,vCurve)
  val machine = c1-&c2-&c3-&c4-&hCurves-&vCurves
  val fsm = (buttons.bottomCenter-^(Phantom(Rectangle(0,0,350,100)).bottomCenter-^machine.topCenter).topCenter)-@(150,50)
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = fsm
  
  val pane = new ScalesPanel()
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
