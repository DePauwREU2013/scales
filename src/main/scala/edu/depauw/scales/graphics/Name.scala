package edu.depauw.scales.graphics

case class Name(name: String, g: Graphic) extends Graphic {
  def render(gc : GraphicsContext) { g.render(gc) }
  
  /** Used to get the bounds of the named graphic
   * 
   * @return the values of the bounds of the named graphic.
   * 
   */  
  override lazy val bounds = g.bounds
  
  override lazy val shape = g.shape
  
  def withName(n: String) = {
    if (n == name) List(g)
    else Nil
  } ++ g.withName(n)
}
  