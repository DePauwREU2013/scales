package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestSmash extends ScalesApp {
  
  // two simple primitives
  val redCircle = Circle(80) -* Colors.Red
  val blueRect = Rectangle(100,50) -* Colors.Blue
    
  // four examples, two with smash and two without
  val g1 = redCircle.smashHeight -^ blueRect
  val g2 = redCircle.smashWidth ||| blueRect
  val g3 = redCircle -^ blueRect
  val g4 = redCircle ||| blueRect
  val panel = GraphicPanel(( g1 ||| g2 ) -^ ( g3 ||| g4 ))
  
  // add the panel to the ScalesApp window
  addPanel(panel)
}
