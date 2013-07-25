package edu.depauw.scales.graphics

import java.awt.{Graphics, Graphics2D, BasicStroke, RenderingHints, Font => jFont, Color}
import javax.swing.{JFrame, JPanel, WindowConstants}
import edu.depauw.scales.ScalesApp

object TestGraphics extends ScalesApp(600, 600, RenderMode.SCALE_TO_FIT, "Graphics Test") {
  
  val g = Line(25, 25, 75, 75)
  val g2 = Fill(Colors.RED, Polygon((10, 10), (22, 80), (25, 25), (22, 10)))
  
  val serifFont = new Font("Serif", FontStyleType.BOLD | FontStyleType.ITALIC)
  val g3 = Text("Hello World!", serifFont) -@ (10,2)
  
  def fun(x: Double, y: Double): Color = RGBA(x*y, (1-x)*y, x*(1-y), (x+1)*(y+1)/4)
  val g4 = Bitmap(fun, 10, 10, 80, 80)
  
  val panel = GraphicPanel()
  panel.graphic = g -& g2 -& g3 -& g4
  
  addPanel(panel)
}
