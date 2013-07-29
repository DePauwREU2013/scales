package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestSmash extends ScalesApp {
  
  // two simple primitives
  val redCircle = Circle(80) -* Colors.RED
  val blueRect = Rectangle(100,50) -* Colors.BLUE
  
  // create a panel to display graphics on
  val panel = GraphicPanel()
  
  // four examples, two with smash and two without
  panel.graphic =
    ( (redCircle.hSmash -^ blueRect) ||| (redCircle.vSmash ||| blueRect) ) -^
    ( (redCircle -^ blueRect) ||| ( redCircle ||| blueRect) )
  
  // add the panel to the ScalesApp window
  addPanel(panel)
}
