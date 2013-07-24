package edu.depauw.scales.reactive

import java.awt.event.{KeyEvent, KeyListener}
import reactive.EventSource

class KeyEventStream extends EventSource[KeyEvent] with KeyListener {
  def keyPressed  (e: KeyEvent) = fire(e)
  def keyReleased (e: KeyEvent) = fire(e)
  def keyTyped    (e: KeyEvent) = fire(e)
}