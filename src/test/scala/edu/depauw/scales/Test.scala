package edu.depauw.scales

case object Foo

case class Test(msg : String, beats : Double) extends Step {
  def act(actor : StepActor) {
    println(msg + " starts: " + actor.startTime)
    actor.director ! Request(actor, actor.endTime, Foo)
    actor.director ! Request(actor.parent, actor.startTime + actor.duration, Done)
    actor.react {
      case Foo => println(msg + " end: " + System.currentTimeMillis)
    }
  }
}

object Demo extends App {
  private val test = (Test("Hello", 1) + Test("Goodbye", 1)) |
    Test("Boo", 1.5) |
    Pedal(Test("A", 0.6) + Test("B", 0.6) + Test("C", 0.6))
  
  private val director = new Director(test)
  
  director.start()
}
