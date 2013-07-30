package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp
import java.awt.geom.AffineTransform

object FernDemo extends ScalesApp(600,600) {
  
  // create panel to draw on
  val panel = GraphicPanel()
  
  // add a Barnsley Fern to the panel
  panel.graphic = barnsley(Polygon(400, 3) -% Math.PI/2 -~* RGBA(0,1,0,0.2))(7) centerAt (width/2,height/2)
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* ================================= METHODS ================================== */ 
  
  /**
   * Freezes a given graphic, creates 4 copies of it, each transformed differently,
   * then composites the result. This is a single iteration of a Barnsley Fern.
   */
  def fernify(g: Graphic): Graphic = {
    val f = g
    val w = f.bounds.getWidth
    val h = f.bounds.getHeight
    //println(f.bounds)
    //println(g.bounds)
    val t1 = new AffineTransform(0.01, 0, 0, 0.16, 0, 0)
    val t2 = new AffineTransform(0.85, -0.04, 0.04, 0.85, 0, 0.15*h)
    val t3 = new AffineTransform(0.2, 0.23, -0.26, 0.22, 0, 0.15*h)
    val t4 = new AffineTransform(-0.15, 0.26, 0.28, 0.24, 0, 0.09*h)
    
    (f -* t1) -& (f-* t2) -& (f -* t3) -& (f -* t4)
  }
  
  /**
   * Creates a stream where each subsequent item is an fernified version
   * of the previous item.
   * @param g Graphic provides the seed graphic to iterate over
   * @return Stream[Graphic] each item in the stream is an iteration of the fern transform
   */
  def barnsley(g: Graphic): Stream[Graphic] = Stream.iterate(g)(fernify)
}