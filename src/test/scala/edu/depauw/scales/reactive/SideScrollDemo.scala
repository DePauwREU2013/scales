package edu.depauw.scales.reactive

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._
import java.awt.geom.AffineTransform
import java.awt.event.KeyEvent

object SpriteState extends Enumeration {
  type SpriteState = (Int, Int)
  val right0 = (208,76)
  val right1 = (368,76)
  val right2 = (328,76)
  val left0 = (168,76)
  val left1 = (8,76)
  val left2 = (48,76)
  val jumpR = (208,114)
}
import SpriteState._

object SideScrollDemo extends ScalesApp(600,600,RenderMode.DEFAULT, "Side Scrolling Demo") {
  
  // path to file
  val filename = System.getProperty("user.dir") + "/resources/smw_mario_sheet.png"
  
  // image, rotated and scaled
  val spriteSheet: Graphic = Image(filename, 392, 784)
  
  type MarioState = (SpriteState, (Double, Double), (Double,Double))
  val initState: MarioState = (SpriteState.right0, (0,0), (0,0))
  
  addPanel(
    ReactivePanel[MarioState](0, new AffineTransform, 
    					  initState, onRenderHandler, 
    					  Some(onTickHandler), 24,
    					  onKeyEvent = Some(onKeyEventHandler))
  )
  
  /**
   * This function renders the string into a series of Text graphics.
   */
  def onRenderHandler: MarioState => Graphic = {
    case ((sx,sy), (x,y), d) =>
      Clip(Rectangle(16,28) -+ (sx,sy),spriteSheet).topLeft -+ (x+width/2, y+height/2)
  }
  
  def onTickHandler: MarioState => MarioState = {
    case (s, (x,y), (dx,dy)) => {
      if (dx > 0) {
        s match {
          case SpriteState.right0 => (SpriteState.right1, (x + 6,y), (dx, dy))
          case SpriteState.right1 => (SpriteState.right2, (x + 6,y), (dx, dy))
          case SpriteState.right2 => (SpriteState.right1, (x + 6,y), (dx, dy))
          case _ => (SpriteState.right1, (x + 6,y), (dx, dy))
        }
      }
      else (s,(x,y),(dx,dy))
    }
  }
  
  /**
   * This function specifies how the string is transformed for each keystroke 
   */
  def onKeyEventHandler: (KeyEvent, MarioState) => MarioState = {
	case (e, (s, p, (dx,dy))) => 
	  e.getID match {
	    case KeyEvent.KEY_PRESSED =>
		      // what was typed?
		    e.getKeyCode match {
		      
		      // if it's a backspace, remove the previous char
		      case KeyEvent.VK_RIGHT => (s,p,(1,dy))
		      case KeyEvent.VK_LEFT => (s,p,(-1,dy))
		      case KeyEvent.VK_UP => (s,p,(dx,1))
		      case KeyEvent.VK_DOWN => (s,p,(dx,-1))
		      
		      // otherwise nothing
		      case _ => (s,p,(dx,dy))
		    }
	    case KeyEvent.KEY_RELEASED => (s, p, (0,0))
	    
	    // other events don't transform the state
	    case _ => (s,p,(dx,dy))
	  }
  }
}