package edu.depauw.scales.reactive

import javax.swing.{JFrame, WindowConstants}
import reactive.Observing
import edu.depauw.scales.graphics.ScalesPanel


object SimpleReactiveTest extends App with Observing {
  val frame = new JFrame("Simple Reactive Test")
  frame.setSize(600, 400)
  
  val pane = new ScalesPanel
  
  // print the mouse events to the console
  for (e <- pane.mouseEventStream) println(e)
  
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
}
