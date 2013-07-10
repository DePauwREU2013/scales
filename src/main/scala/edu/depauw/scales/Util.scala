package edu.depauw.scales

import language.implicitConversions

object Util {
  implicit class IntMultiply(n: Int) {
    def *(step : Step) : Step = step * n
  }
  
  implicit class IntStretch(n : Int) {
    def ~(step : Step) : Step = step ~ n.toDouble
  }
  
  implicit class DoubleStretch(x : Double) {
    def ~(step : Step) : Step = step ~ x
  }
  
  implicit def intInterpolator(x : Int) = new DoubleInterpolator(x.toDouble)
  
  implicit class DoubleInterpolator(x : Double) {
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
