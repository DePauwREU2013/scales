package edu.depauw.scales.reactive

import java.awt.event.{MouseEvent, MouseListener}
import reactive.EventSource

class MouseEventStream extends EventSource[MouseEvent] with MouseListener {
  def mouseClicked  (e: MouseEvent) = fire(e)
  def mouseEntered  (e: MouseEvent) = fire(e)
  def mouseExited   (e: MouseEvent) = fire(e)
  def mousePressed  (e: MouseEvent) = fire(e)
  def mouseReleased (e: MouseEvent) = fire(e)
}