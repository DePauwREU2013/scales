package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints, Font => jFont, Color}
import javax.swing.{JFrame, JPanel, WindowConstants}

object TestShapes extends App {
  val frame = new JFrame("Graphics Test")
  frame.setSize(600, 400)
  val serifFont = new Font("Serif", FontStyleType.ITALIC, 28)
 
  
  val row1 = List(
      Circle(50).center -^ Text("Circle", serifFont).padTop(10).center,
      Ellipse(0, 0, 50, 100).center -^ Text("Ellipse", serifFont).padTop(10).center,
      Rectangle(0, 0, 50, 100).center -^ Text("Rectangle", serifFont).padTop(10).center
      ).foldLeft(Phantom -@ (40, 20))(hSpace(50))
  
  val row2 = List(
      Polygon(50, 9).center -^ Text("Polygon", serifFont).padTop(10).center,
      Polygon((0,0), (50,50), (75,100), (50,100), (25,75), (0,0)).center -^ Text("Polygon", serifFont).padTop(10).center,
      Square(100).center -^ Text("Square", serifFont).padTop(10).center
      ).foldLeft(Phantom -@ (40, 20))(hSpace(50))
  
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = row1 -^ row2.padTop(40)
  
  val pane = new ScalesPanel
  pane.add(panel)
  frame.add(pane)
  
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  // composites two graphics, the second being top-aligned and horizontally separated by `dx`
  def hSpace(dx: Double): (Graphic, Graphic) => Graphic = {
    case (composite, g) => composite -& (g. -@ (composite.tr._1 + dx, composite.tr._2))
  } 
}
