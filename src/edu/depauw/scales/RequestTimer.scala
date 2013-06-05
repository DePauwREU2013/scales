package edu.depauw.scales

import java.util.Timer
import java.util.TimerTask

class RequestTimer(director : Director) {
  class NotifyTask extends TimerTask {
    def run {
	  next = null
      task = null
	  director ! Notify
    }
  }
	
  private var next : Request = null
  
  private var task : TimerTask = null
  
  private val timer : Timer = new Timer(true)
  
  def schedule(r : Request) {
    // Only schedule r if it happens sooner than next (or there is no next)
    if (next == null || next < r) {
      next = r
      
      if (task != null) task.cancel()    
      task = new NotifyTask()
      val delay = r.time - System.currentTimeMillis
      if (delay <= 0) {
        task.run()
      } else {
        timer.schedule(task, delay)
      }
    }
  }
}
