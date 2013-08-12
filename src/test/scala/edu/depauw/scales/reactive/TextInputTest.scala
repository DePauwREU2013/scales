package edu.depauw.scales
package reactive

import graphics._

import java.awt.geom.AffineTransform
import java.awt.event.KeyEvent

object TextInputTest extends ScalesApp(600,600,RenderMode.DEFAULT, "Text Input Test") {
  
  addPanel(
    ReactivePanel[String](0, new AffineTransform, 
    					  "start typing", onRenderHandler, onKeyEvent = Some(onKeyEventHandler))
  )
  
  /**
   * This function renders the string into a series of Text graphics.
   */
  def onRenderHandler: String => Graphic = {
    case str => 
      val newlineChars = Array[Char](10,13)
      
      // split the string by new lines
      str.split(newlineChars).foldLeft(Blank(): Graphic)({
        
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