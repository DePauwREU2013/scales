package edu.depauw.scales.graphics

import java.awt.{BasicStroke, RenderingHints, Font => JFont, Graphics, Graphics2D}
import javax.swing.JPanel

import scala.collection.mutable.ArrayBuffer
import edu.depauw.scales.reactive._

/**
 * Modes to use when rendering.
 */
object RenderMode extends Enumeration {
  type RenderMode = Value
  
  /** Uses pixel-based rendering. If a graphic is 100 width, it will use 100 pixels to represent it. */
  val DEFAULT = Value("default")
  /** Scale all graphics to fit, both window dimensions. Does not preserve aspect ratio! */
  val SCALE_TO_FIT = Value("scaleToFit")
  /** Scales proportionally so that all graphics fit within the window.*/
  val FIT_MAX = Value("fitMax")
  /** Scales proportionally so that the graphic always fills at least the entire window. May result in clipping!*/
  val FIT_MIN = Value("fitMin")
  /** Scales to fit using 100.0 in both horizontal and vertical directions. */
  val PERCENT = Value("percent")
}
import RenderMode._

/**
 * This class implements a JPanel to which GraphicPanels can be added.
 * It is responsible for invoking render calls on the panels it contains.
 * 
 * @param mode RenderMode determines how the panel scales GraphicPanels.
 */
class ScalesPanel(mode: RenderMode = RenderMode.DEFAULT) extends JPanel {
  
  // create buffer to hold the panels
  val panels = new ArrayBuffer[GraphicPanel]
  
  /**
   *  EventStream for MouseEvents
   */ 
  lazy val mouseEventStream = {
    val mes = new MouseEventStream      
    addMouseListener(mes)
    mes
  }
  
  /**
   * EventStream for MouseMotionEvents
   */
  lazy val mouseMotionEventStream = {
    val mmes = new MouseMotionEventStream      
    addMouseMotionListener(mmes)
    mmes
  }
  
  /**
   * EventStream for MouseInputEvents (= MouseEvents + MouseMotionEvents)
   */
  lazy val mouseInputEventStream = mouseMotionEventStream | mouseEventStream
  
  /**
   * EventStream for KeyEvents
   */
  lazy val keyEventStream = {
    val kes = new KeyEventStream
    setFocusable(true)
    addKeyListener(kes)
    kes
  }
    
  setBackground(Colors.Clear)
  setOpaque(false)
  
  
  /* =============================== METHODS ================================== */
  
  /**
   * adds a GraphicPanel to be displayed
   * @param panel GraphicPanel the panel to be added
   */
  def add(panel : GraphicPanel) = synchronized {
    var i = 0
    while (i < panels.size && panel > panels(i)) i += 1
    panels.insert(i, panel)
    panel.parentChangedStream.fire(Some(this))
  }
  
  /**
   * removes a GraphicPanel
   * @param panel GraphicPanel the panel to be removed
   */
  def remove(panel : GraphicPanel) = synchronized {
    panels -= panel
    panel.parentChangedStream.fire(None)
  }
  
  /**
   * @return yields the maximum combined dimensions of all `panels` attached
   */
  def getPanelDimensions: (Double, Double) = {
    // start from (0,0), then find maximum components
    panels.foldLeft((0.0, 0.0))(
      (dims, panel) => {
        val bounds = panel.getGraphic.bounds
        (dims._1 max bounds.getMaxX, dims._2 max bounds.getMaxY)
      }
    )
  }
  
  /**
   * Invokes a paint procedure using a Graphics instance.
   * The component will call the render method for each panel in panels.
   */
  override def paintComponent(graphics: Graphics) = synchronized {
    // call super's implementation first
    super.paintComponent(graphics)
    
    val g2d = graphics.asInstanceOf[Graphics2D]
      
    // find current panels' dimensions
    val (dimX, dimY) = getPanelDimensions
    
    // set scaling for given RenderMode
    if (mode != RenderMode.DEFAULT && dimX > 0 && dimY > 0) {
      mode match {
        case RenderMode.SCALE_TO_FIT => g2d.scale(getWidth / dimX, getHeight / dimY)
        case RenderMode.FIT_MAX => {
          val s = Math.min(getWidth / dimX, getHeight / dimY)
          g2d.scale(s,s)
        }
        case RenderMode.FIT_MIN => {
          val s = Math.max(getWidth / dimX, getHeight / dimY)
          g2d.scale(s,s)
        }
        case RenderMode.PERCENT => {
          g2d.scale(getWidth / 100.0, getHeight / 100.0)
        }
      }
    }
    
    // default stroke, font, rendering hints...
    g2d.setStroke(new BasicStroke(2.0f / g2d.getTransform.getScaleX.toFloat, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER))
    g2d.setFont(new JFont("SansSerif", JFont.PLAIN, 5))
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                         RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
    
    // for each panel
    for (panel <- panels) {
      // remember old transform
      val oldTransform = g2d.getTransform
      
      // switch to the panel's transform
      g2d.transform(panel.transform)
      
      // create a GraphicContext
      val gc = new GraphicsContext(g2d)
      
      // render the graphic on the GraphicPanel
      panel.render(gc)
      
      // restore the old transform
      g2d.setTransform(oldTransform)
    }
  }
}
