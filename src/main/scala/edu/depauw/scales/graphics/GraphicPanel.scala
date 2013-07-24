package edu.depauw.scales.graphics

import java.awt.Container
import java.awt.geom.AffineTransform
import reactive._
import edu.depauw.scales.reactive._
import java.awt.event.{KeyEvent, MouseEvent}

class GraphicPanel(val layer : Int, val transform : AffineTransform) extends Ordered[GraphicPanel] {
  def compare(that : GraphicPanel) : Int = that.layer - this.layer
  // this is greater (higher priority) than that if it has a smaller layer number
  
  var graphic: Graphic = Phantom
  lazy val parentChangedStream: EventSource[Option[ScalesPanel]] = new EventSource[Option[ScalesPanel]]
}

object ReactivePanel extends Observing {
  
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		  	   onTickEvent: Option[T => T] = None, fps: Int = 30,
		       onMouseEvent: Option[(MouseEvent, T) => T] = None,
		       onMouseMotionEvent: Option[(MouseEvent, T) => T] = None,
		       onMouseInputEvent: Option[(MouseEvent, T) => T] = None,
		       onKeyEvent: Option[(KeyEvent, T) => T] = None): GraphicPanel = {
    
    var lastState: T = state
    val panel = new GraphicPanel(layer, transform)
    panel.graphic = onRender(state)
    for (parent <- panel.parentChangedStream) {
      parent match {
        case None => panel.graphic = onRender(lastState)
        case Some(sp: ScalesPanel) => {
          
          if (onTickEvent.isDefined) {
            for (e <- (new Timer(interval = 1000/fps))) {
              lastState = onTickEvent.get(lastState)
              panel.graphic = onRender(lastState)
              sp.repaint()
            }
          }
          
          if (onMouseEvent.isDefined) {
	        for {
	          listener <- onMouseEvent
	          e <- sp.mouseEventStream
	        } {
	          lastState = listener(e,lastState)
	          panel.graphic = onRender(lastState)
	          sp.repaint()
	        }
          }
          
          if (onMouseMotionEvent.isDefined) {
	        for {
	          listener <- onMouseMotionEvent
	          e <- sp.mouseMotionEventStream
	        } {
	          lastState = listener(e,lastState)
	          panel.graphic = onRender(lastState)
	          sp.repaint()
	        }
          }
          
          if (onMouseInputEvent.isDefined) {
	        for {
	          listener <- onMouseInputEvent
	          e <- sp.mouseInputEventStream
	        } {
	          lastState = listener(e,lastState)
	          panel.graphic = onRender(lastState)
	          sp.repaint()
	        }
          }
          
          if (onKeyEvent.isDefined) {
	        for {
	          listener <- onKeyEvent
	          e <- sp.keyEventStream
	        } {
	          lastState = listener(e,lastState)
	          panel.graphic = onRender(lastState)
	          sp.repaint()
	        }
          }
        }
      }
    }
    panel
  }
  
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onMouseEvent: (MouseEvent, T) => T): GraphicPanel = 
		         apply(layer, transform, state, onRender, onMouseEvent = Some(onMouseEvent))
    
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onTickEvent: T => T): GraphicPanel = 
		         apply(layer, transform, state, onRender, Some(onTickEvent))
    
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onTickEvent: T => T, fps: Int): GraphicPanel = 
		         apply(layer, transform, state, onRender, Some(onTickEvent), fps)
}