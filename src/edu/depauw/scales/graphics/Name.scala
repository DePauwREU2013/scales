package edu.depauw.scales.graphics

case class Name(name: String, g: Graphic) extends Graphic {
  def render(gc : GraphicsContext) { g.render(gc) }
  
  def bounds = g.bounds
  
  def withName(n: String) = {
    if (n == name) List(g)
    else Nil
  } ++ g.withName(n)
}
  