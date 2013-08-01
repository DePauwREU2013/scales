package edu.depauw.scales

import edu.depauw.scales.graphics._

case class DrawFrame(n : Int)

case class Anim(fn : Double => Graphic, beats : Double) extends Step {
  def act(actor : StepActor) {
    val fps = actor.context.fps
    val period = (1000 / fps).toInt  // msec per frame
    val numFrames = actor.duration / period
    
    val panel = new GraphicPanel(actor.layers._1, actor.context.transform, fn(0))
    
    actor.director ! AddPanel(panel)
    
    actor.director ! Request(actor, actor.startTime + actor.duration, Done)
    
    scheduleFrame(1, actor, period)
    more(actor, panel, period, numFrames)
  }
  
  def reverse = Anim((x : Double) => fn(1.0-x), beats)
  
  def scheduleFrame(n : Int, actor : StepActor, period : Int) {
    actor.director ! Request(actor, actor.startTime + period * n, DrawFrame(n))
  }
  
  def more(actor : StepActor, panel : GraphicPanel, period : Int, numFrames : Int) {
    actor.react {
	  case DrawFrame(n) => {
        panel.setGraphic(fn(n.toDouble / numFrames))
          
        if (n < numFrames) {
          scheduleFrame(n + 1, actor, period)
        }
        
        more(actor, panel, period, numFrames)
	  }
   
      case Done => {
        actor.director ! RemovePanel(panel)
        
        actor.parent ! Done
      }
	}
  }
  
  override def ~(dur : Double) = Anim(this.fn, dur * this.beats)
}
