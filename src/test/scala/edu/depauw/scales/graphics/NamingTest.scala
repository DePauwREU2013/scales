package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object NamingTest extends ScalesApp(600, 600, RenderMode.PERCENT, "Naming Test") {
  
  // create some named primitives
  val circle     = Name("circle", Circle(20))
  val redCircle  = Name("red"   , Circle(10) -* Colors.RED)
  val blueSquare = Name("blue"  , Square(10) -* Colors.BLUE)
  
  // composite them into a single graphic
  val unlabeled = circle.changeBounds(0, 0, 50, 50) -|| Translate(0, 20, redCircle -|| blueSquare) -&
  				  (blueSquare -^ blueSquare -^ blueSquare)
  
  // create panel to render to
  val panel = GraphicPanel(unlabeled -&
                  labelWithName(unlabeled, "red") -&
                  labelWithName(unlabeled, "blue") -&
                  labelWithName(unlabeled, "fakeOut") -&
                  labelWithName(unlabeled, "circle"))
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* =============================== METHODS ===================================== */
  
  /**
   * Creates a label consisting of text scaled and centered to fit with a given graphic
   * @param g Graphic the graphic to be labeled
   * @param text String the text to be used for the label
   * @return Graphic the label as a Text graphic, scaled and centered about g
   */
  def label(g: Graphic, text: String): Graphic = {
    val l = Text(text)
    val scaleRatio = 0.8* g.bounds.getWidth / l.bounds.getWidth
    Translate(g.bounds.getCenterX, g.bounds.getCenterY, 
              Scale(scaleRatio, scaleRatio, l).center)
  }
  
  /**
   * Finds all sub-graphics with a given name and then generates labels
   * for them, compositing the resulting labels into a single graphic.
   * @param g Graphic the graphic to search for names
   * @param name String the name to search for in the graphic; also the label text
   * @return Graphic a single graphic of labels for graphics which matched `name`
   */
  def labelWithName(g: Graphic, name: String): Graphic = 
    g.withName(name).map(label(_, name)).foldLeft(Phantom: Graphic)(_ -& _)
}