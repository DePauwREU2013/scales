package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

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

object BSTTest extends ScalesApp{
  val bst = makeBST(List(20,10,30,15,25,11,29,5,40,16,1,6,39,42))
  def makeBST(nums: List[Int],t: Tree = Empty):Tree = nums match{
    case head::tail => makeBST(tail,t.insert(head))
    case Nil => t
    case _ => Empty
  }
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
  
  
  
  // composite them on a panel
  val panel = GraphicPanel()
  panel.graphic = Translate(this.width/2,this.height/2,drawTree(bst).center)
  
  // add panel to window
  addPanel(panel)
}
