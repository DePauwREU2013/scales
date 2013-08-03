package edu.depauw.scales

import language.implicitConversions

/**
 * Typesafe angle units. A function with an Angle parameter will not accept a Double.
 * Conversions are defined so that, for example, "180 deg" will produce the same Angle
 * as "Pi rad" or "0.5 rev". Value classes (AnyVal) mean this does no object-creation.
 */
class Angle(val inRadians: Double) extends AnyVal {
  def inDegrees: Double = math.toDegrees(inRadians)
  
  def inRevolutions: Double = inRadians / Util.Tau
  
  override def toString: String = inRadians + " rad"
  
  def +(a: Angle): Angle = new Angle(inRadians + a.inRadians)
  
  def -(a: Angle): Angle = new Angle(inRadians - a.inRadians)
  
  def *(d: Double): Angle = new Angle(inRadians * d)
  
  def /(d: Double): Angle = new Angle(inRadians / d)
}
  
object Util {
  implicit class IntMultiply(n: Int) {
    def *(step: Step): Step = step * n
  }
  
  implicit class IntStretch(n : Int) {
    def ~(step: Step): Step = step ~ n.toDouble
  }
  
  implicit class DoubleStretch(x: Double) {
    def ~(step: Step): Step = step ~ x
  }
  
  implicit def intInterpolator(x: Int) = new DoubleInterpolator(x.toDouble)
  
  implicit class DoubleInterpolator(x: Double) {
    def -->(y: Double) = {v: Double => (1 - v) * x + v * y}
    
    def quadIn_-->(y: Double) = {v: Double => (1 - v) * (1 - v) * (x - y) + y}
    
    def quadOut_-->(y: Double) = {v: Double => x + v * v * (y - x)}
    
    def circIn_-->(y: Double) = {v: Double => x + Sin(v/4 rev) * (y - x)}
    
    def circOut_-->(y: Double) = {v: Double => Cos(v/4 rev) * (x - y) + y}
  }
  
  val Pi = math.Pi
  val Tau = 2 * Pi
  
  implicit val postfixOps = language.postfixOps // enables us to write "90 deg" without warning
  
  implicit class AngleFactory(val d: Double) extends AnyVal {
    def deg = new Angle(math.toRadians(d))
    def rad = new Angle(d)
    def rev = new Angle(d * Tau)
    
    def *(a: Angle): Angle = new Angle(d * a.inRadians)
  }
  
  def Sin(ang: Angle) = math.sin(ang.inRadians)
  
  def Cos(ang: Angle) = math.cos(ang.inRadians)
  
  def Tan(ang: Angle) = math.tan(ang.inRadians)
}
