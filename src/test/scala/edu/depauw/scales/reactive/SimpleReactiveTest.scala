package edu.depauw.scales.reactive

import edu.depauw.scales.ScalesApp
import reactive.Observing

/*
 * Note we are manually adding reactive.Obsevering to the App here.
 * Alternatively, one can use the ReactivePanel, which handles all
 * this under the hood. However, this manual way of doing this 
 * allows one to exploit the reactivity directly, which could be 
 * very useful in integrating other components from the Swing 
 * library.
 */
object SimpleReactiveTest extends ScalesApp with Observing {
  
  // print the mouse events to the console
  for (e <- scalesPanel.mouseEventStream) println(e)
  
}
