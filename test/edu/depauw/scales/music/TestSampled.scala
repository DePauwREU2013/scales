package edu.depauw.scales.music;

import edu.depauw.scales._

/*Bugs:  throws unsupported audio file format exception for some files.  */
object DemoSampled extends Application {
  val test = new Clip(0, System.getProperty("user.dir") + "/resources/bassoon-g4.wav")
  
  val director = new Director(test + C + test + E + G + C.> + G + E + C + test~3)
  director.start()
}
