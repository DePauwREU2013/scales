package edu.depauw.scales.graphics

import java.awt.Container
import java.awt.geom.AffineTransform
import reactive._
import edu.depauw.scales.reactive._
import java.awt.event.MouseEvent

class GraphicPanel(val layer : Int, val transform : AffineTransform) extends Ordered[GraphicPanel] {
  def compare(that : GraphicPanel) : Int = that.layer - this.layer
  // this is greater (higher priority) than that if it has a smaller layer number
  
  var graphic: Graphic = Phantom
  lazy val parentChangedStream: EventSource[Option[ScalesPanel]] = new EventSource[Option[ScalesPanel]]
}

object ReactivePanel extends Observing {
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic) = {
    val panel = new GraphicPanel(layer, transform)
    panel.graphic = onRender(state)
    panel
  }
  
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onMouseEvent: (MouseEvent, T) => T) = {
    var lastState: T = state
    val panel = new GraphicPanel(layer, transform)
    panel.graphic = onRender(state)
    for (parent <- panel.parentChangedStream) {
      parent match {
        case None => panel.graphic = onRender(lastState)
        case Some(sp: ScalesPanel) => {
          for (e <- sp.mouseEventStream) {
            lastState = onMouseEvent(e,lastState)
            panel.graphic = onRender(lastState)
            sp.repaint()
          }
        }
      }
    }
    panel
  }
}