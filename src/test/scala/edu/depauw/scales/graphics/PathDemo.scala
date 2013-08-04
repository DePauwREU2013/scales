package edu.depauw.scales
package graphics

import edu.depauw.scales.ScalesApp

object PathDemo extends ScalesApp {
  import Util._
  
  // a fibonacci spiral
  val spiral = Path((60, 100) -% (180 deg)
                    -? (50,90) -% (90 deg)
                    -? (60,80) -% (0 deg)
                    -? (80,100) -% (270 deg)
                    -? (50,130) -% (180 deg)
                    -? (0,80) -% (90 deg)
                    -? (80,0) -% (0 deg)
                    -? (210,130) -% (270 deg)
                    -- (210,130))
  
  // the corresponding fibonacci rectangle
  val fibRect = (Square(80)-^(Square(50)|||(((Square(10)-^Square(10)|||Square(20)))-^Square(30)))) ||| Square(130)
  
  // composite them on a panel
  val panel = GraphicPanel((spiral -& fibRect) -* 3)
  
  // add panel to window
  addPanel(panel)
}
