package edu.depauw.scales.reactive

import edu.depauw.scales.graphics._

import javax.swing.{JFrame, WindowConstants}
import java.awt.event.KeyEvent
import java.awt.geom.AffineTransform

trait Tree {
  def insert(n: Int): Tree
}
object Empty extends Tree {
  def insert(n: Int): Tree = new Node(Empty, n, Empty)
}
case class Node(val l: Tree,val v: Int,val r: Tree) extends Tree {
  def insert(n: Int): Tree = if(n<v){
	  new Node(l.insert(n),v,r)
      } else if(n>v){
        new Node(l,v,r.insert(n))
      } else this
}

object ReactiveBST extends App {
  val frame = new JFrame("Text Input Test")
  frame.setSize(600, 600)
  
  val pane = new ScalesPanel
  type TreeMaker = (String,List[Int])
  val initState = ("",Nil)
  
  val panel = ReactivePanel[TreeMaker](0, new AffineTransform, 
		  	    initState, onRenderHandler, onKeyEvent = Some(onKeyEventHandler))
  
  pane.add(panel)
  frame.add(pane)
  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
  frame.setVisible(true)
  /*
   * This function goes through a list of ints and builds a BST
   */
  def makeBST(nums: List[Int],t: Tree = Empty):Tree = nums match{
    case head::tail => makeBST(tail,t.insert(head))
    case Nil => t
    case _ => Empty
  }
  /*
   * Takes a BST and returns a Graphic
   */
  def drawTree(t: Tree): Graphic = t match{
    case Empty => Blank()
    case t:Node => 
      val center = Circle(15).center -& Text(t.v.toString).center
      val left = Name("left"+t.v, if(t.l!=Empty) drawTree(t.l) else Blank())
      val right = Name("right"+t.v, if(t.r!=Empty) drawTree(t.r) else Blank())
      val noLines = center.center.padBottom(20) above 
          (left beside Phantom(Square(20)) beside right).center
      val leftLine = t.l match{
        case Empty => Blank()
        case Node(l,v,r) => Line(noLines.withName("left" + t.v)(0).tc,center.bc)
      }
      val rightLine = t.r match{
        case Empty => Blank()
        case Node(l,v,r) => Line(noLines.withName("right" + t.v)(0).tc,center.bc)
      }
      noLines -& rightLine -& leftLine
  }
  
  /**
   * This function renders the string into a series of Text graphics.
   */
  def onRenderHandler: TreeMaker => Graphic = {
    case (s,l) => (drawTree(makeBST(l)).center -+ (frame.getWidth/2,frame.getHeight/2)) -&
    (Text("Type a Integer and hit enter").padBottom(20).center-^
        Text(if(s.equals("")) " " else s,FontSize(20)).center -+ (frame.getWidth/2,35))
  }
  
  /**
   * This function specifies how the string and list are transformed for each keystroke 
   */
  def onKeyEventHandler: (KeyEvent, TreeMaker) => TreeMaker = {
	case (e, (s,l)) => 
	  
	  // check if a char has been typed
	  if (e.getID == KeyEvent.KEY_TYPED) {
	    
	     // what was typed?
	    e.getKeyChar match {
	      
	      case c if c.equals("") => (s,l)
	      case KeyEvent.VK_MINUS => (if(s.contains("-")) (s.drop(1),l) else ("-" + s,l))
	      // if it's a backspace, remove the previous char
	      case KeyEvent.VK_BACK_SPACE => (s.dropRight(1),l)
	      //if it's enter, turn the string into an Int and append it to the list
	      case KeyEvent.VK_ENTER => ("",l:::List(Integer.parseInt(s)))
	      
	      // if it's a number, append it 
	      case c  => if(c.isDigit) (s + c,l) else (s,l)
	    }
	  } // other events don't transform the string
	  else (s,l)
  }
}