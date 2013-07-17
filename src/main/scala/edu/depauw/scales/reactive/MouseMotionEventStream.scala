package edu.depauw.scales.reactive

import java.awt.event.MouseMotionListener
import reactive.EventSource
import java.awt.event.MouseEvent

class MouseMotionEventStream extends EventSource[MouseEvent] with MouseMotionListener {
  def mouseDragged(e: MouseEvent) = fire(e)
  def mouseMoved(e: MouseEvent) = fire(e)
}