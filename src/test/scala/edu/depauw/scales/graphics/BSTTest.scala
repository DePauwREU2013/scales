package edu.depauw.scales.graphics

import edu.depauw.scales.ScalesApp

trait Tree {
  def insert(n: Int): Tree = this match{
    case Empty => new Node(Empty,n,Empty)
    case t: Node =>
      if(n<t.v){
        return new Node(t.l.insert(n),t.v,t.r)
      } else if(n>t.v){
        return new Node(t.l,t.v,t.r.insert(n))
      } else return this
  }
}
object Empty extends Tree
case class Node(val l: Tree,val v: Int,val r: Tree) extends Tree

object BSTTest extends ScalesApp{
  val bst = makeBST(List(2,1,3,4))
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
          (left.padRight(20) beside right).center
      val leftLine = t.l match{
        case Empty => Blank()
        case t:Node => Line(noLines.withName("left" + t.v)(0).tc,center.bc)
      }
      val rightLine = t.r match{
        case Empty => Blank()
        case t:Node => Line(noLines.withName("right" + t.v)(0).tc,center.bc)
      }
      noLines -& rightLine -& leftLine
  }
  
  
  
  // composite them on a panel
  val panel = GraphicPanel()
  panel.graphic = Translate(this.width/2,this.height/2,drawTree(bst).center)
  
  // add panel to window
  addPanel(panel)
}
