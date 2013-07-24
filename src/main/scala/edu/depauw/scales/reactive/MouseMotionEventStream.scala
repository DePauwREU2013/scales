package edu.depauw.scales.reactive

import java.awt.event.{MouseEvent, MouseMotionListener}
import reactive.EventSource

class MouseMotionEventStream extends EventSource[MouseEvent] with MouseMotionListener {
  def mouseDragged (e: MouseEvent) = fire(e)
  def mouseMoved   (e: MouseEvent) = fire(e)
}