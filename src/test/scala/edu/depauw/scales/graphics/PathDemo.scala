package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

object PathDemo extends ScalesApp {
  
  // a fibonacci spiral
  val spiral = Scale(5, 4.5, Path(new PointSegment(30,60).curveTo(20,50).heading(math.Pi).
      curveTo(30,40).heading(math.Pi/2).curveTo(40,60).heading(0).curveTo(20,80).heading(3*math.Pi/2).
      curveTo(0,40).heading(math.Pi).curveTo(40,0).heading(math.Pi/2).curveTo(100,80).heading(0).
      lineTo(100,80).heading(3*math.Pi/2)))
  
  // the corresponding fibonacci rectangle
  val fibRect =
    Scale(5, 4.5,
      (Square(40) -^
        (
          Rectangle(0.0,0.0,20.0,40.0) -||
          (
            (
              (
                Square(10) -^
                Square(10)
              ) -||
              Rectangle(0,0,10,20)
            ) -^
            Square(20)
          )
        )
      ) -||
      Rectangle(0,0,60,80)
    )
  
  // composite them on a panel
  val panel = GraphicPanel()
  panel.graphic = spiral -& fibRect
  
  // add panel to window
  addPanel(panel)
}
