package edu.depauw.scales

import edu.depauw.scales.graphics._
import edu.depauw.scales.music._
import Util._

object AnimDemo extends App {
  
  def resourcePath(fileName: String): String =
    System.getProperty("user.dir") + "/resources/" + fileName
  
  // a bunch of stuff to show in parallel and in sequence
  val test: Step =
    // magic jellybean jumping along to a fifth repeated 10 times
    2 * Anim(bounce, 5) | (C~.5 + G.<.en) * 10 +
    // bean gets hyper as music speeds up
    (5~Anim(bounce, 1) | (.5~C.> + G.en) * 5)~2.5 +
    // the bean vanishes when a low C is played
    C.< |
    // everything below is running the entire time:
    Anim(grow, 15) |
    // date time readout
    FPS(1, Anim({_ => Text(new java.util.Date().toString, FontSize(6)) -+ (10, 95) }, 17)) |
    // DePauw CS REU advisors
    Pict(Image(resourcePath("examplePicture.jpg"), 80, 80, 10, 10), 17) |
    // pretty sure this inspired Jony Ive's iOS 7 design
    Pict(Bitmap({(x, y) => RGBA(x, Math.min(x, y), y, 0.5)}, 100, 100), 17) |
    // why not add a frame?!
    Pict(Square(90) -+ (5,5) -~* Colors.YELLOW, 17)
  
  val director = new Director(test, new ScalesPanel(RenderMode.PERCENT))
  director.start()
  
  /* a magical colorful jellybean riding a sin wave */
  def bounce(v: Double): Graphic =
	Shear(0.5, 0,
	  Circle((5 --> 30)(v), (10 --> 70)(v), 50 + 30 * Sin(5 * v rev)) -+ (-40,0)
	) -* HSV(2 * v, 1.0, 1.0) -~ 0.5
  
  /* A growing, rotating blue bowtie polygon thingy */
  def grow(v: Double): Graphic = Fill(RGBA(0, 0, 1, 0.5), 
    Polygon((0,0),(100,100),(100,0),(0,100)) -% (v rev) -* v centerAt (50,50)
  )
}