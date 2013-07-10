package edu.depauw.scales

import edu.depauw.scales.graphics._

// Specialization of Anim for static pictures
case class Pict(g : Graphic, beats : Double) extends Step {
  def act(actor : StepActor) {
    val panel = new GraphicPanel(actor.layers._1, actor.context.transform)
    panel.graphic = g
    
    actor.director ! AddPanel(panel)
    
    actor.director ! Request(actor, actor.startTime + actor.duration, Done)

    actor.react {
      case Done => {
        actor.director ! RemovePanel(panel)
        
        actor.parent ! Done
      }
	}
  }
  
  def reverse = this
  
  override def ~(dur : Double) = Pict(this.g, dur * this.beats)
}
