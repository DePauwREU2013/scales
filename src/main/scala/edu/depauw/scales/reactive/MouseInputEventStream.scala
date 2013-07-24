package edu.depauw.scales.reactive

import java.awt.event.{MouseEvent, MouseMotionListener}

class MouseInputEventStream extends MouseEventStream with MouseMotionListener {
  def mouseDragged (e: MouseEvent) = fire(e)
  def mouseMoved   (e: MouseEvent) = fire(e)
}