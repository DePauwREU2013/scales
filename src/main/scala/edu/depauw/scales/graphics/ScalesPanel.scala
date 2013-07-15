package edu.depauw.scales.graphics

import java.awt.{BasicStroke, RenderingHints, Font => JFont, Graphics, Graphics2D}
import javax.swing.JPanel

import scala.collection.mutable.ArrayBuffer

object RenderMode extends Enumeration {
  type RenderMode = Value
  val DEFAULT = Value("default")
  val SCALE_TO_FIT = Value("scaleToFit")
}

import RenderMode._

class ScalesPanel(mode: RenderMode = RenderMode.DEFAULT) extends JPanel {
    val MAX_X = 100.0
    val MAX_Y = 100.0
    
    val panels = new ArrayBuffer[GraphicPanel]

    def add(panel : GraphicPanel) = synchronized {
      var i = 0
      while (i < panels.size && panel > panels(i)) i += 1
      panels.insert(i, panel)
    }
  
    def remove(panel : GraphicPanel) = synchronized {
      panels -= panel
    }
    
    setBackground(Colors.CLEAR)
    setOpaque(false)

    override def paintComponent(graphics : Graphics) = synchronized {
      super.paintComponent(graphics)
      
      val g2d = graphics.asInstanceOf[Graphics2D]
      mode match {
          case RenderMode.SCALE_TO_FIT => g2d.scale(getWidth / MAX_X, getHeight / MAX_Y)
          case _ => Nil
      }
      g2d.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER))
      g2d.setFont(new JFont("SansSerif", JFont.PLAIN, 5))
      g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                           RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
      for (panel <- panels) {
        val oldTransform = g2d.getTransform
        g2d.transform(panel.transform)
        val gc = new GraphicsContext(g2d)
        panel.graphic.render(gc)
        g2d.setTransform(oldTransform)
      }
    }
  }
