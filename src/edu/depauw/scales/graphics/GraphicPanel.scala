package edu.depauw.scales.graphics

import java.awt.Container
import java.awt.geom.AffineTransform

class GraphicPanel(val layer : Int, val transform : AffineTransform) extends Ordered[GraphicPanel] {
  def compare(that : GraphicPanel) : Int = that.layer - this.layer
  // this is greater (higher priority) than that if it has a smaller layer number
  
  var graphic : Graphic = Blank
}
