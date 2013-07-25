package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestTextAlignment extends ScalesApp(600, 600, RenderMode.SCALE_TO_FIT, "Text Alignment Test") {
  
  // some simple text elements
  val t1 = Text("Hello")
  val t2 = Text("World")
  
  // two different ways of writing "H2O"
  val t3 = Text("H") -|| Translate(0, 2, Text("2", FontSize(4))) -|| Text("O")
  val t4 = (Text("H").bottomRight -& Text("2", FontSize(4)).topLeft).topRight -& Text("O").topLeft
  
  // a circle with text at its center
  val t5 = Circle(20).center -& t4.center
  
  // create a panel to draw to
  val panel = GraphicPanel()
  
  // add the composite of the above graphics
  panel.graphic = Translate(10, 10, t1 ||| t2) -&
  				  Translate(10, 80, t1 -^ t2) -&
  				  Translate(10, 40, t3) -&
  				  Translate(10, 60, t4) -&
  				  Translate(70, 60, t5)
  
  // add the panel to the ScalesApp window
  addPanel(panel)
}