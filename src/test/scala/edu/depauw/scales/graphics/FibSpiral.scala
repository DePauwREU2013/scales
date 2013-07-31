package edu.depauw.scales
package graphics

import edu.depauw.scales.ScalesApp

object FibSpiral extends ScalesApp(640,480,RenderMode.FIT_MAX, "Fibonacci Spiral") {
  import Util._
  
  // a fibonacci spiral
  val spiral = Path((60.0,100.0).curveTo(50,90).heading(180 deg).
      curveTo(60,80).heading(90 deg).curveTo(80,100).heading(0 deg).curveTo(50,130).heading(270 deg).
      curveTo(0,80).heading(180 deg).curveTo(80,0).heading(90 deg).curveTo(210,130).heading(0 deg).
      lineTo(210,130).heading(270 deg))
  def fib(num: Int) : Int = num match
  {
    case 1 => 1
    case 2 => 1
    case _ => fib(num-1) + fib (num-2)
    
  }
  
  def fibRect (num: Int ): Graphic = num match
  {
    case 1 => Square(1)
    case _ => (num % 4) match 
    		   {
    		   		case 2 => Square(fib(num)) -^ fibRect(num-1)
    		        case 1 => Square(fib(num)) ||| fibRect(num-1)
    		        case 0 =>  fibRect(num-1) -^ Square(fib(num)) 
    		        case 3 =>  fibRect(num-1) ||| Square(fib(num))
    		   }
  }
  
  
  val panel = GraphicPanel(fibRect(20))
  
  // add panel to window
  addPanel(panel)
}