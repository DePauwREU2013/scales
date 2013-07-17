package edu.depauw.scales.reactive

import javax.swing.{JFrame, WindowConstants}
import java.awt.event.MouseEvent
import reactive.Observing

import edu.depauw.scales.graphics._

object ReactiveGraphicTest extends App with Observing {
  val frame = new JFrame("Simple Reactive Test")
  frame.setSize(600, 400)
  
  val pane = new ScalesPanel(RenderMode.SCALE_TO_FIT)
  val panel = new GraphicPanel(0, new java.awt.geom.AffineTransform())
  panel.graphic = Text("Move your mouse") -&
  				  Translate(0, 10, Text("over this window")) -@ (20,30)
  
  for (e <- pane.mouseEventStream) {
    panel.graphic = e.getID match {
      case MouseEvent.MOUSE_ENTERED => Text("Mouse Entered") -@ (20,15)
      case MouseEvent.MOUSE_EXITED => Text("Mouse Exited") -@ (20,30)
      case MouseEvent.MOUSE_PRESSED => Text("Mouse Pressed") -@ (20,45)
      case MouseEvent.MOUSE_RELEASED => Text("Mouse Released") -@ (20,60)
      case MouseEvent.MOUSE_CLICKED => Text("Mouse Clicked") -@ (20,75)
    }
    pane.repaint()
  }
  
  pane.add(panel)
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
