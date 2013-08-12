package edu.depauw.scales.graphics

import java.awt.geom.AffineTransform
import java.awt.event.{KeyEvent, MouseEvent}
import reactive.{EventSource, Observing, Timer}

/**
 * The object on which Graphics get placed.
 */
class GraphicPanel(val layer: Int, val transform: AffineTransform,
    private var graphic: Graphic = Blank()) extends Ordered[GraphicPanel] with Observing {
  // TODO the transform doesn't seem to be used...
  
  // method to determine the order of a panel (lower number yields higher priority)
  def compare(that : GraphicPanel) : Int = that.layer - this.layer
  
  // change the graphic, and also notify parent (if any) to repaint
  private[scales] def setGraphic(g: Graphic) {
    graphic = g
    for (p <- parent) p.repaint()
  }
  
  def getGraphic: Graphic = graphic
  
  def render(gc: GraphicsContext) {
    graphic.render(gc)
  }
  
  private var parent: Option[ScalesPanel] = None
  
  // this provides an interface through which the parent can be determined asynchronously
  lazy val parentChangedStream: EventSource[Option[ScalesPanel]] = new EventSource[Option[ScalesPanel]]
  
  // set up an observer to update the parent
  for (p <- parentChangedStream) {
    parent = p
  }
}

/**
 * Basic instance of a GraphicPanel, where layer = 0 and transform is the identity
 */
object GraphicPanel {
  def apply(g: Graphic = Blank()): GraphicPanel = new GraphicPanel(0, new AffineTransform, g)
}

/**
 * A reactive implementation of the GraphicPanel.
 * 
 * ==Overview==
 * This object implements the specification for a "World Program" as described in the paper
 * "A Functional I/O System: or, Fun for Freshman Kids" (Felleisen et al. 2009).  Essentially,
 * it is a state machine which evolves state in reaction to various EventStreams. At an interval
 * specified by `fps`, the machine will use the `onRender` method to generate a graphic
 * from the current state.  This graphic is then what is rendered visually in the panel.
 * 
 * ==State==
 * The type of the state of the machine is generic. An initial state of this type must be given.
 * In general it is recommended that the type of state follow the adage, "as simple as possible, yet
 * as complex as necessary" to render a graphic using the information contained in the state data
 * structure. It is further recommended that the state type be given an explicit type declaration.
 * 
 * ==Evolving State==
 * The reactive aspect of the machine is determined by the various event handling methods. 
 * Each event handler is responsible for accepting an input of a given Event type, together with an
 * instance of the previous state, and determining what the following state must be. For example,
 * the `onMouseEvent` method will be passed a MouseEvent corresponding to the triggering 
 * event (e.g., a mouse click) and a state instance.  Using this information, a new state should be
 * returned. If the state changes, the previous graphic will be invalidated and a new one will be 
 * rendered once a time interval has elapsed.
 * 
 * The `onTickEvent` can be used for general animation. If specified, it will be called every frame
 * and will be passed the previous state. Note: When specified, it will be invoked immediately
 * *prior* to the `onRender` call.
 * 
 */
object ReactivePanel extends Observing {
  
  /** Principal constructor for the ReactivePanel
   * @param layer the layer number
   * @param transform the transform of the GraphicPanel
   * @param state the essential information required to render a graphic and evolve the machine state
   * @param onRender specifies how the current `state` translates into a `Graphic`
   * @param onTickEvent specifies how the machine evolves from one state to the next on each tick
   * @param fps the number of frames (ticks) per second
   * @param onMouseEvent specifies how the machine evolve from one state to the next on a MouseEvent
   * @param onMouseMotionEvent specifies how the machine evolve from one state to the next on a MouseMotionEvent
   * @param onMouseInputEvent specifies how the machine evolve from one state to the next on a MouseInputEvent
   * @param onKeyEvent specifies how the machine evolve from one state to the next on a KeyEvent
   * @return a GraphicPanel which implements a functional I/O
   */
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		  	   onTickEvent: Option[T => T] = None, fps: Int = 30,
		       onMouseEvent: Option[(MouseEvent, T) => T] = None,
		       onMouseMotionEvent: Option[(MouseEvent, T) => T] = None,
		       onMouseInputEvent: Option[(MouseEvent, T) => T] = None,
		       onKeyEvent: Option[(KeyEvent, T) => T] = None): GraphicPanel = {
    
    // keep track of previous state
    var lastState: T = state
    
    // flag to invalidate the current graphic
    var invalid: Boolean = true
    
    // panel to add graphics to
    val panel = new GraphicPanel(layer, transform)
    
    // ansychronously obtain reference to the parent ScalesPanel
    for (parent <- panel.parentChangedStream) {
      
      // check if a parent is found
      parent match {
        
        // now that we have a ScalesPanel reference...
        case Some(sp: ScalesPanel) => {
          
          // Let's wire up all our EventStreams
          
          // MouseEventStream
          if (onMouseEvent.isDefined) {
	        for {
	          listener <- onMouseEvent
	          e <- sp.mouseEventStream
	        } {
	          val nextState = listener(e,lastState)
	          invalid = invalid || (nextState != lastState)
	          lastState = nextState
	        }
          }
          
          // MouseMotionEventStream
          if (onMouseMotionEvent.isDefined) {
	        for {
	          listener <- onMouseMotionEvent
	          e <- sp.mouseMotionEventStream
	        } {
	          val nextState = listener(e,lastState)
	          invalid = invalid || (nextState != lastState)
	          lastState = nextState
	        }
          }
          
          // MouseInputEventStream
          if (onMouseInputEvent.isDefined) {
	        for {
	          listener <- onMouseInputEvent
	          e <- sp.mouseInputEventStream
	        } {
	          val nextState = listener(e,lastState)
	          invalid = invalid || (nextState != lastState)
	          lastState = nextState
	        }
          }
          
          // KeyEventStream
          if (onKeyEvent.isDefined) {
	        for {
	          listener <- onKeyEvent
	          e <- sp.keyEventStream
	        } {
	          val nextState = listener(e,lastState)
	          invalid = invalid || (nextState != lastState)
	          lastState = nextState
	        }
          }
          
          // last, but not least, the primary render loop
          for (e <- (new Timer(interval = 1000/fps))) {
            
            // TickEvent handler
            if (onTickEvent.isDefined) {
              val nextState = onTickEvent.get(lastState)
	          invalid = invalid || (nextState != lastState)
	          lastState = nextState
            }
            
            // did anyone invalidate it?
            if (invalid) {
              
              // if so, update the graphic
              panel.setGraphic(onRender(lastState))
              sp.repaint()
              
              // and feel validated in doing so
              invalid = false
            }
            
          } // close render loop
          
        } // close Some(ScalesPanel) case
        
        /*
         * NOTE: Hmmm...we should probably disabling the reactivity in some way here
         */
        case None => panel.setGraphic(onRender(lastState))
        
      } // close parent match
      
    } // close parentChangedStream foreach
    
    // return the awesomifyed panel
    panel
  }
  
  
  /**
   * MouseEvent-only ReactivePanel constructor overload
   */
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onMouseEvent: (MouseEvent, T) => T): GraphicPanel = 
		         apply(layer, transform, state, onRender, onMouseEvent = Some(onMouseEvent))
    
  /**
   * TickEvent-only ReactivePanel constructor overload
   */
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onTickEvent: T => T): GraphicPanel = 
		         apply(layer, transform, state, onRender, Some(onTickEvent))
    
  /**
   * Custom `fps`, TickEvent-only ReactivePanel constructor overload
   */
  def apply[T](layer: Int, transform: AffineTransform, state: T, onRender: T => Graphic,
		       onTickEvent: T => T, fps: Int): GraphicPanel = 
		         apply(layer, transform, state, onRender, Some(onTickEvent), fps)
}