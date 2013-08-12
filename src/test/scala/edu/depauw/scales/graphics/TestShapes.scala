package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object TestShapes extends ScalesApp {
  
  // pick a pretty font
  val serifFont = new Font("Serif", FontStyleType.ITALIC, 28)
  
  // create a row of examples
  val row1: Graphic = List(
      // circle
      Circle(50).center -^ Text("Circle", serifFont).padTop(10).center,
      
      // ellipse
      Ellipse(50, 100).center -^ Text("Ellipse", serifFont).padTop(10).center,
      
      // rectangle
      Rectangle(50, 100).center -^ Text("Rectangle", serifFont).padTop(10).center
      
      // composite them with a horizontal space of 50 (see `hSpace` method below)
      ).foldLeft(Blank() -@ (40, 40))(hSpace(60))
  
  // create another row
  val row2: Graphic = List(
      
      // regular polygon, in this case a nonagon
      Polygon(50, 9).center -^ Text("Polygon", serifFont).padTop(10).center,
      
      // irregular polygon, in this case a pentagon
      Polygon((0,0), (50,50), (75,100), (50,100), (25,75), (0,0)).center -^ Text("Polygon", serifFont).padTop(10).center,
      
      // square
      Square(100).center -^ Text("Square", serifFont).padTop(10).center
      
      // combine these just like the others
      ).foldLeft(Blank() -@ (40, 40))(hSpace(60))
  
  // panel to draw on; place the rows vertically with 60px padding
  val panel = GraphicPanel(row1 -^ row2.padTop(60))
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* ====================== METHODS ====================== */
  
  // composites two graphics, the second being top-aligned and horizontally separated by `dx`
  def hSpace(dx: Double): (Graphic, Graphic) => Graphic = {
    case (composite, g) => composite -& (g. -@ (composite.tr._1 + dx, composite.tr._2))
  } 
}
