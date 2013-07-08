package edu.depauw.scales

import edu.depauw.scales.graphics._
import edu.depauw.scales.music._
import Util._

object AnimDemo extends App {
  def resourcePath(fileName: String): String = System.getProperty("user.dir") + "/resources/" + fileName
  
  val test : Step =
    2 * Anim(bounce, 5) |
    (C~.5 + G.<.en) * 10 +
      (5~Anim(bounce, 1) | (.5~C.> + G.en) * 5)~2.5 +
      C.< |
    Anim(grow, 15) |
    FPS(1, Anim({_ => Translate(10.0, 95.0, Text(new java.util.Date().toString))}, 17)) |
    Pict(Image(resourcePath("examplePicture.jpg"), 10, 10, 80, 80), 17) |
    Pict(Bitmap({(x, y) => RGBA(x, Math.min(x, y), y, 0.5)}, 0, 0, 100, 100), 17) |
    Pict(Outline(Colors.YELLOW, Fill(Colors.YELLOW, Rectangle(5, 5, 90, 90))), 17)

  val director = new Director(test)
  director.start()
  
  def bounce(v : Double) : Graphic =
    Stroke(0.5,
      Shear(0.5, 0, 50, 50,
        Translate(10, 0,
            Fill(HSV(2 * v, 1.0, 1.0),
              Circle((5 --> 30)(v), (10 --> 70)(v), 50 + 30 * sin2pi(5 * v))))))
  
  def grow(v : Double) : Graphic =
    Scale(v, v / 2, 50, 50,
      Rotate(2 * Pi * v, 50, 50,
        Fill(RGBA(0, 0, 1, 0.5),
          Polygon((0, 0), (100, 100), (100, 0), (0, 100)))))
}