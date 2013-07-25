package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestSmash extends ScalesApp() {
  
  // two simple primitives
  val redCircle = Fill(Colors.RED, Circle(80))
  val blueRect = Fill(Colors.BLUE,Rectangle(0,0, 100,50))
  
  // create a panel to display graphics on
  val panel = GraphicPanel()
  
  // four examples, two with smash and two without
  panel.graphic =
    ( (redCircle.hSmash -^ blueRect) ||| (redCircle.vSmash ||| blueRect) ) -^
    ( (redCircle -^ blueRect) ||| ( redCircle ||| blueRect) )
  
  // add the panel to the ScalesApp window
  addPanel(panel)
}
