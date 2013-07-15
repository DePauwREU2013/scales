package edu.depauw.scales

import edu.depauw.scales.graphics._
import edu.depauw.scales.music._
import Util._

object Juggling extends App {
  val air = Anim(ballAirA, .5) + Anim(ballAirB, .5)
  val right = Anim(ballHandRightA, .25) + Anim(ballHandRightB, .25)
  val backA = Anim(ballAirBackA, .5)
  val backB = Anim(ballAirBackB, .5)
  val back = backA + backB
  val left = Anim(ballHandLeftA, .25) + Anim(ballHandLeftB, .25)
  val man = Anim(manA, .5) + Anim(manB, .5)
  
  val juggler = (air + right + back + left)*4 |
    (right + back + left + air)*4 |
    (backB + left + air + right + backA)*4 |
    man*12  // Anim(jugglerMan, 1)*12
  
  val moon = System.getProperty("user.dir") + "/resources/moon.jpg"
  
  val director = new Director(juggler + FPS(4, juggler) + juggler~6 +
                                (Tempo(30, juggler) |
                                   Pict(Image(moon, 0, 0, 100, 100), 24)),
                              new ScalesPanel(RenderMode.SCALE_TO_FIT))
  
  director.start()
  
  def ball(x : Double, y : Double) : Graphic = Fill(Colors.RED, Circle(2, x, y))
  
  def ballAirA(v : Double) = ball((48 --> 58)(v), (48 quadIn_--> 20)(v))
  def ballAirB(v : Double) = ball((58 --> 68)(v), (20 quadOut_--> 48)(v))
  
  def ballHandRightA(v : Double) = ball((68 circOut_--> 60)(v), (48 circIn_--> 53)(v))
  def ballHandRightB(v : Double) = ball((60 circIn_--> 52)(v), (53 circIn_--> 48)(v))
  
  def ballAirBackA(v : Double) = ball((52 --> 42)(v), (48 quadIn_--> 20)(v))
  def ballAirBackB(v : Double) = ball((42 --> 32)(v), (20 quadOut_--> 48)(v))
  
  def ballHandLeftA(v : Double) = ball((32 circOut_--> 40)(v), (48 circIn_--> 53)(v))
  def ballHandLeftB(v : Double) = ball((40 circIn_--> 48)(v), (53 circOut_--> 48)(v))
  
  val headAndBody = Stroke(1, Fill(Colors.YELLOW, Ellipse(45, 10, 10, 15) |
                                     Polygon((40,28), (60,28), (55,50), (45,50))))

  def manA(v : Double) =
    Stroke(.5, Line(40, 28, (39 --> 41)(v), 48) |
               Line((39 --> 41)(v), 48, 40 + 8*cos2pi(v/2), 48 - 5*sin2pi(v/2)) |
               Line(60, 28, (59 --> 61)(v), 48) |
               Line((59 --> 61)(v), 48, 60 + 8*cos2pi(v/2), 48 + 5*sin2pi(v/2))) |
      headAndBody
  
  def manB(v : Double) =
    Stroke(.5, Line(40, 28, (41 --> 39)(v), 48) |
               Line((41 --> 39)(v), 48, 40 - 8*cos2pi(v/2), 48 + 5*sin2pi(v/2)) |
               Line(60, 28, (61 --> 59)(v), 48) |
               Line((61 --> 59)(v), 48, 60 - 8*cos2pi(v/2), 48 - 5*sin2pi(v/2))) |
      headAndBody
  
  def jugglerMan(v : Double) : Graphic = {
    if(v < .5) {
      Stroke(.5, Line(40, 28, 39+(v*4), 48) |                               //left arm
             Line(39+(v*4), 48, 40+8*cos2pi(1-v), 48+5*sin2pi(1-v)) |       //left hand
             Line(60, 28, 59+(v*4), 48) |                                   //right arm
             Line(59+(v*4), 48, 60+8*cos2pi(v), 48+5*sin2pi(v))) |          //right hand
        headAndBody
    } else {
      Stroke(.5, Line(40, 28, 41-((v-.5)*4), 48) |                          //left arm
             Line(41-((v-.5)*4), 48, 40+8*cos2pi(1-v), 48+5*sin2pi(1-v)) |  //left hand
             Line(60, 28, 61-((v-.5)*4), 48) |                              //right arm
             Line(61-((v-.5)*4), 48, 60+8*cos2pi(v), 48+5*sin2pi(v))) |     //right hand
        headAndBody
    }
  }
}