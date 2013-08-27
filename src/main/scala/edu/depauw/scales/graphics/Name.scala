package edu.depauw.scales.graphics

/**
 * Wrapper to add a string identifier (a name!) to the given Graphic
 */
case class Name(name: String, g: Graphic) extends Graphic {
  def render(gc: GraphicsContext) { g.render(gc) }

  /**
   * unchanged from original graphic
   */
  override lazy val bounds = g.bounds

  /**
   * unchanged from original graphic
   */
  override lazy val shape = g.shape

  /**
   * a list containing this graphic plus any matched-by-name graphics it contains is returned
   */
  def withName(n: String) = {
    if (n == name) List(g)
    else Nil
  } ++ g.withName(n)
  
  def names: Set[String] = g.names + name
}
