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
  val DEFAULT = Value("default")
  val SCALE_TO_FIT = Value("scaleToFit")
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
    
  setBackground(Colors.CLEAR)
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
   * Invokes a paint procedure using a Graphics instance.
   * The component will call the render method for each panel in panels.
   */
  override def paintComponent(graphics : Graphics) = synchronized {
    
    // call super's implementation first
    super.paintComponent(graphics)
    
    // Graphics2D object is mutable
    var g2d = graphics.asInstanceOf[Graphics2D]
    
    // set scaling for given RenderMode
    mode match {
      case RenderMode.SCALE_TO_FIT => g2d.scale(getWidth / 100.0, getHeight / 100.0)
      case _ => Nil
    }
    
    // default stroke, font, renderinghints...
    g2d.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER))
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
      panel.graphic.render(gc)
      
      // restore the old transform
      g2d.setTransform(oldTransform)
    }
  }
}
