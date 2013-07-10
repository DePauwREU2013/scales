package edu.depauw.scales.graphics

import javax.swing.JFrame
import javax.swing.WindowConstants

object NamingTest extends App {
  val frame = new JFrame("Alignment Test")
  frame.setSize(600, 600)
  
  val g1 = Name("circle", Circle(20))
  val g2 = Name("red", Fill(Colors.RED, Circle(10)))
  val g3 = Name("blue", Fill(Colors.BLUE, Square(10)))
  
  val unlabeled = g1.changeBounds(0, 0, 50, 50) -|| Translate(0, 20, g2 -|| g3) -&
  				  (g3 -^ g3 -^ g3)
  				  
  def label(g: Graphic, text: String): Graphic = {
    val l = Text(text)
    val scaleRatio = 0.8* g.bounds.getWidth / l.bounds.getWidth
    Translate(g.bounds.getCenterX(), g.bounds.getCenterY(), 
              Scale(scaleRatio, scaleRatio, l).center)
  }
  
  def labelWithName(g: Graphic, name: String): Graphic = {
    g.withName(name).map(label(_,name)).foldLeft(Blank():Graphic)(_ -& _)
  }
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = unlabeled -&
                  labelWithName(unlabeled, "red") -&
                  labelWithName(unlabeled, "blue") -&
                  labelWithName(unlabeled, "fakeOut") -&
                  labelWithName(unlabeled, "circle")
                  
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}