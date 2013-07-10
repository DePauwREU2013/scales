package edu.depauw.scales.graphics

case class Name(name: String, g: Graphic) extends Graphic {
  def render(gc : GraphicsContext) { g.render(gc) }
  
  def bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(n: String) = {
    if (n == name) List(g)
    else Nil
  } ++ g.withName(n)
}
  