package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestSmash extends ScalesApp {
  
  // two simple primitives
  val redCircle = Circle(80) -* Colors.RED
  val blueRect = Rectangle(100,50) -* Colors.BLUE
  
  // create a panel to display graphics on
  val panel = GraphicPanel()
  
  // four examples, two with smash and two without
  val g1 = redCircle.hSmash -^ blueRect
  val g2 = redCircle.vSmash ||| blueRect
  val g3 = redCircle -^ blueRect
  val g4 = redCircle ||| blueRect
  panel.graphic = ( g1 ||| g2 ) -^ ( g3 ||| g4 )
  
  // add the panel to the ScalesApp window
  addPanel(panel)
}
