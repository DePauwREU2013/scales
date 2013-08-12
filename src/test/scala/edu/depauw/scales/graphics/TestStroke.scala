package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestStroke extends ScalesApp {
  val g = Rectangle(50, 50).pad(20)
  val h = g.stroke(4)
  val panel = GraphicPanel((g ||| g.stroke(10)) -^
        (g.scale(0.5) ||| g.stroke(10).scale(0.5) ||| g.scale(0.5).stroke(10)) -^
        (h ||| h.stroke(10)) -^
        (h.scale(0.5) ||| h.stroke(10).scale(0.5) ||| h.scale(0.5).stroke(10)))
  
  addPanel(panel)
}