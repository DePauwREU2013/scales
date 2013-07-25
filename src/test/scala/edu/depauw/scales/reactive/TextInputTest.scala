package edu.depauw.scales.reactive

import edu.depauw.scales.graphics._

import javax.swing.{JFrame, WindowConstants}
import java.awt.event.KeyEvent
import java.awt.geom.AffineTransform

object TextInputTest extends App {
  val frame = new JFrame("Text Input Test")
  frame.setSize(600, 600)
  
  val pane = new ScalesPanel
  
  val panel = ReactivePanel[String](0, new AffineTransform, 
		  	    "start typing", onRenderHandler, onKeyEvent = Some(onKeyEventHandler))
  
  pane.add(panel)
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  
  /**
   * This function renders the string into a series of Text graphics.
   */
  def onRenderHandler: String => Graphic = {
    case str => 
      val newlineChars = Array[Char](10,13)
      
      // split the string by new lines
      str.split(newlineChars).foldLeft(Phantom: Graphic)({
        
        // for multiple new lines, add padding
        case (g, "") => g.padBottom(14)
        
        // append a Text graphic for each line
        case (g,line) => g -^ Text(line, FontSize(14)).padTop(7)
        
      }) -@ (10,10)
  }
  
  /**
   * This function specifies how the string is transformed for each keystroke 
   */
  def onKeyEventHandler: (KeyEvent, String) => String = {
	case (e, s) => 
	  
	  // check if a char has been typed
	  if (e.getID == KeyEvent.KEY_TYPED) {
	    
	     // what was typed?
	    e.getKeyChar match {
	      
	      // if it's a backspace, remove the previous char
	      case KeyEvent.VK_BACK_SPACE => s.dropRight(1)
	      
	      // otherwise, append the new one
	      case c => s + c
	    }
	  } // other events don't transform the string
	  else s
  }
}