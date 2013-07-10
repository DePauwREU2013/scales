package edu.depauw.scales

import scala.actors.Actor


trait StepActor extends Actor {
  def startTime : Long
  def duration : Int // in milliseconds
  def endTime : Long
  
  val parent : StepActor
  val context : Context
  val director : Director = if (parent != null) parent.director else this.asInstanceOf[Director]
  val layers : (Int, Int)
}

object StepActor {
  def apply(step : Step, parent : StepActor, startTime : Long,
            context : Context, layers : (Int, Int)) : StepActor = {
    val actor = new StepActorImpl(step, parent, startTime, context, layers)
    actor.start()
    actor
  }
}

class StepActorImpl(step : Step, val parent : StepActor, val startTime : Long,
                    val context : Context, val layers : (Int, Int)) extends StepActor {
  def act() {
    step.act(this)
  }
  
  def duration : Int = (step.beats * context.msecPerBeat).toInt
  
  lazy val endTime : Long = context.endTimeFun(this)
}
