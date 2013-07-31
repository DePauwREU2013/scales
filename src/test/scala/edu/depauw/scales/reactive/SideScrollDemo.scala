package edu.depauw.scales.reactive

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._
import java.awt.geom.AffineTransform
import java.awt.event.KeyEvent

/** An object to manage the state of the sprite */
object SpriteState extends Enumeration {
  type SpriteState = (Direction, Action)
  
  type Direction = Value
  val LEFT: Direction = Value("left")
  val RIGHT: Direction = Value("right")
  
  type Action = Value
  val STAND: Action = Value("stand")
  val WALK_1: Action = Value("walk1")
  val WALK_2: Action = Value("walk2")
  val JUMP: Action = Value("jump")
  val FALL: Action = Value("fall")
  
  val spriteMap = Map[SpriteState, (Double,Double)](
      ((SpriteState.LEFT , SpriteState.STAND ), (168, 76)),
      ((SpriteState.LEFT , SpriteState.WALK_1), (  8, 76)),
      ((SpriteState.LEFT , SpriteState.WALK_2), ( 48, 76)),
      ((SpriteState.LEFT , SpriteState.JUMP  ), (168,114)),
      ((SpriteState.LEFT , SpriteState.FALL  ), (128,114)),
      ((SpriteState.RIGHT, SpriteState.STAND ), (208, 76)),
      ((SpriteState.RIGHT, SpriteState.WALK_1), (368, 76)),
      ((SpriteState.RIGHT, SpriteState.WALK_2), (328, 76)),
      ((SpriteState.RIGHT, SpriteState.JUMP  ), (208,114)),
      ((SpriteState.RIGHT, SpriteState.FALL  ), (248,114))
  )
}

object SideScrollDemo extends ScalesApp(600,470,RenderMode.DEFAULT, "Side Scrolling Demo") {
  import SpriteState._
  
  // paths to files
  val spritesPath = System.getProperty("user.dir") + "/resources/smw_mario_sheet.png"
  val levelPath = System.getProperty("user.dir") + "/resources/level.png"
  
  // load the sprite sheet and level
  val spriteSheet: Graphic = Image(spritesPath, 392, 784)
  val level: Graphic = Image(levelPath, 1024, 432)
  
  // the game state info
  type MarioState = (SpriteState, (Double, Double), (Double,Double))
  
  // where everything starts off
  val initState: MarioState = ((SpriteState.RIGHT, SpriteState.STAND), (-240,0), (0,0))
  
  // create the panel and load it into the window
  addPanel(
    ReactivePanel[MarioState](0, new AffineTransform,
    					  initState, onRenderHandler,
    					  Some(onTickHandler), 24,
    					  onKeyEvent = Some(onKeyEventHandler))
  )
  
  /**
   * This function renders the state into a graphical presentation
   */
  def onRenderHandler: MarioState => Graphic = {
    case (s, (x,y), d) =>
      val sprite = Clip(Rectangle(16,32) -+ SpriteState.spriteMap(s),spriteSheet).topLeft
      level -& (sprite -+ (x+width/2, y+ 357))
  }
  
  /**
   * Given the current state, this determines the subsequent sprite
   */
  def nextSprite(state: MarioState): SpriteState = state match {
    case ((dir, act), (x,y), (dx,dy)) => 
      
      // calculate the Direction
      val nextDir = {
        if (dx > 0) SpriteState.RIGHT
        else if (dx < 0) SpriteState.LEFT
        else dir
      }
      
      // calculate the Action
      val nextAct = {
        
	    if (y == 0 && dy == 0) { // not jumping
	      
	      // either standing or walking
		  if (dx == 0) SpriteState.STAND
		  else if (act == SpriteState.WALK_1) SpriteState.WALK_2
		  else SpriteState.WALK_1
		  
	    } else if (dy > 0) SpriteState.JUMP
	    else SpriteState.FALL
      }
    
    // return sprite state
    (nextDir, nextAct)
  }
  
  /**
   *   Determines the subsequent position and motion.
   *   This is where any physics should occur.
   *   
   *   Note: Everything in here is frame-based. There are no references to time.
   *   
   */
  def stepMotion(state: MarioState): MarioState = state match {
    case (s, (x,y), (dx,dy)) =>
      
      // position
      val nextX = x + 16 * dx
      val nextY = Math.min(0, y - dy * 4)
      
      // rate of rising or falling
      val nextDY = if (nextY == 0) 0 else dy - 1
      
      // no forces alter the 
      (s, (nextX, nextY), (dx, nextDY))
  }
  
  /**
   * Handler to determine the frame based evolution of the state
   */
  def onTickHandler: MarioState => MarioState = {
    case m => 
      val nextStep = stepMotion(m)
      (nextSprite(nextStep), nextStep._2, nextStep._3)
  }
  
  /**
   * Handler to specify how key events alter the state.
   */
  def onKeyEventHandler: (KeyEvent, MarioState) => MarioState = {
	case (e, (s, p, (dx,dy))) => 
	  e.getID match {
	    case KeyEvent.KEY_PRESSED =>
	      
		  // what was typed?
		  e.getKeyCode match {
		    
		    // we only like arrows
		    case KeyEvent.VK_RIGHT => (s,p,(1,dy)) // move right
		    case KeyEvent.VK_LEFT => (s,p,(-1,dy)) // move left
		     
		    // UP arrow is only effective when on the ground
		    case KeyEvent.VK_UP if p._2 == 0 => (s,p,(dx,6))
		    
		    // otherwise preserve the state
		    case _ => (s,p,(dx,dy))
		  }
		 
	    case KeyEvent.KEY_RELEASED => e.getKeyCode match {
		      
		  // these is no momentum; releasing left or right stops horizontal movement
		  case KeyEvent.VK_RIGHT => (s,p,(0,dy))
		  case KeyEvent.VK_LEFT => (s,p,(0,dy))
		      
		  // ignore other key releases
		  case _ => (s,p,(dx,dy))
	    }
	    
	    // other events don't transform the state
	    case _ => (s,p,(dx,dy))
	  }
  }
}