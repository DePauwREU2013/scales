package edu.depauw.scales.reactive

import java.awt.event.{KeyEvent, KeyListener}
import reactive.EventSource

/**
 * Adapter class which interfaces as KeyListener and wraps events as an EventSource.
 */
class KeyEventStream extends EventSource[KeyEvent] with KeyListener {
  def keyPressed  (e: KeyEvent) = fire(e)
  def keyReleased (e: KeyEvent) = fire(e)
  def keyTyped    (e: KeyEvent) = fire(e)
}