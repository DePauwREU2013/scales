package edu.depauw.scales


trait Step {
  val beats : Double
  
  def act(actor : StepActor) : Unit
  
 /** reverse function
  *  
  *  @param nil
  *  @return Step
  * 
  */
  
  def reverse : Step
  
  
   /** 
  *  
  *  @param Integer
  *  @return Step
  * 
  */
  def *(count : Int) : Step = {
    var result : Step = Sequ()
    for (i <- 0 until count) {
      result = result + this
    }
    result
  }
  
 
   /** Shortcut function that joins keys to play in sequence
  *  
  *  @param step of sequence
  *  @return Sequence
  * 
  */
  def +(step : Step) : Step = step match {
    case s : Sequ => Sequ(List(this) ++ s.children : _*)
    case _ => Sequ(this, step)
  }
  
  
    /** Shortcut function that joins sequences to play in parallel
  *  
  *  @param sequence
  *  @return Parallel sequence
  * 
  */
  def |(step : Step) : Step = step match {
    case p : Para => Para(List(this) ++ p.children : _*)
    case _ => Para(this, step)
  }
  
  
    /** Shortcut function to indicate duration of notes
  *  
  *  @param Double
  *  @return Note duration
  * 
  */
  def ~(beats : Double) : Step = Stretch(beats, this)
}



case class Sequ(children : Step*) extends Step {
  lazy val beats : Double = {
    var result = 0.0
    for (child <- children) {
      result += child.beats
    }
    result
  }
  
  def act(actor : StepActor) {
    startNext(actor, 0, actor.startTime)
  }
  

 /** reverse function for a sequence
  *  
  *  @param nil
  *  @return Sequence with reversed children
  * 
  */
  
  def reverse = Sequ(children.map(_.reverse).reverse:_*)
  
  private def startNext(actor : StepActor, i : Int, nextTime : Long) {
    if (i < children.length) {
      val newActor = StepActor(children(i), actor, nextTime, actor.context, actor.layers)
      actor.react {
        case Done => startNext(actor, i + 1, nextTime + newActor.duration)
      }
    } else {
      actor.parent ! Done
    }
  }
  
  override def +(step : Step) : Step = step match {
    case s : Sequ => Sequ(children ++ s.children : _*)
    case _ => Sequ(children ++ List(step) : _*)
  }
}

case class Para(children : Step*) extends Step {
  lazy val beats : Double = {
    var result = 0.0
    for (child <- children) {
      val dur = child.beats
      if (dur > result) result = dur
    }
    result
  }
  
  def act(actor : StepActor) {
    val minLayer = actor.layers._1
    val maxLayer = actor.layers._2
    val stepLayer = (maxLayer - minLayer) / children.length
    
    var first = minLayer
    for (child <- children) {
      StepActor(child, actor, actor.startTime, actor.context, (first, first + stepLayer))
      first += stepLayer
    }
    
    waitFor(actor, children.length)
  }
  
   /** reverse function for Parallel
  *  
  *  @param nil
  *  @return children running in Parallel reversed
  * 
  */
 
  def reverse = Para(children.map(_.reverse):_*)
  
  private def waitFor(actor : StepActor, n : Int) {
    if (n == 0) {
      actor.parent ! Done
    } else {
	  actor.react {
	    case Done => waitFor(actor, n - 1)
	  }
    }
  }
  
  override def |(step : Step) : Step = step match {
    case p : Para => Para(children ++ p.children : _*)
    case _ => Para(children ++ List(step) : _*)
  }
}

case class Style(sheet : Sheet, step : Step) extends Step {
  lazy val beats : Double = step.beats
  
  def act(actor : StepActor) {
    StepActor(step, actor, actor.startTime, actor.context + sheet, actor.layers)
    actor.react {
      case Done => actor.parent ! Done
    }
  }
  
   /** reverse function for Style
  *  
  *  @param nil
  *  @return Style with steps reversed
  * 
  */
  
  def reverse = Style(sheet, step.reverse)
}

case class Stretch(beats : Double, step : Step) extends Step {
  def act(actor : StepActor) {
    StepActor(step, actor, actor.startTime, actor.context + new Sheet {
                override val msecPerBeatOpt = Some(actor.duration / step.beats)
              }, actor.layers)
    actor.react {
      case Done => actor.parent ! Done
    }
  }
  
    
   /** reverse function for Stretch
  *  
  *  @param nil
  *  @return Stretch with steps reversed
  * 
  */
  
  def reverse = Stretch(beats, step.reverse)
  
}
