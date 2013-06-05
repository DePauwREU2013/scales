package edu.depauw.scales

object Util {
  implicit def intMultiply(n : Int) = new AnyRef {
    def *(step : Step) : Step = step * n
  }
  
  implicit def intStretch(n : Int) = new AnyRef {
    def ~(step : Step) : Step = step ~ n.toDouble
  }
  
  implicit def doubleStretch(x : Double) = new AnyRef {
    def ~(step : Step) : Step = step ~ x
  }
  
  implicit def intInterpolator(x : Int) = doubleInterpolator(x.toDouble)
  
  implicit def doubleInterpolator(x : Double) = new AnyRef {
    def -->(y : Double) = {v : Double => (1 - v) * x + v * y}
    
    def quadIn_-->(y : Double) = {v : Double => (1 - v) * (1 - v) * (x - y) + y}
    
    def quadOut_-->(y : Double) = {v : Double => x + v * v * (y - x)}
    
    def circIn_-->(y : Double) = {v : Double => x + Math.sin(Pi * v / 2) * (y - x)}
    
    def circOut_-->(y : Double) = {v : Double => Math.cos(Pi * v / 2) * (x - y) + y}
  }
  
  val Pi = math.Pi
  
  def sin2pi(x : Double) = Math.sin(2 * Pi * x)
  
  def cos2pi(x : Double) = Math.cos(2 * Pi * x)
}
