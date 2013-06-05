package edu.depauw.scales

import scala.actors.Actor
import scala.collection.mutable.PriorityQueue

import javax.sound.midi.MidiSystem
import javax.swing.JFrame
import javax.swing.WindowConstants

import edu.depauw.scales.graphics.{GraphicPanel, ScalesPanel}

case class Request(node : Actor, time : Long, message : Any) extends Ordered[Request] {
  def compare(that : Request) : Int = (that.time - this.time).toInt
  // this is greater (higher priority) than that if it occurs sooner (smaller time)
}

case object Notify

case class AddPanel(panel : GraphicPanel)

case class RemovePanel(panel : GraphicPanel)

case object Done

class Director(step : Step) extends StepActor {
  private val pq = new PriorityQueue[Request]
  
  private val timer = new RequestTimer(this)
  
  private var done = false
  
  var startTime = 0L
  def duration = (step.beats * context.msecPerBeat).toInt
  def endTime = context.endTimeFun(this)
  
  val synth = MidiSystem.getSynthesizer
  synth.open()
  
  private val frame = new JFrame("Scales")
  frame.setSize(800, 600)
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  
  val parent = null
  val context = new BaseContext(this)
  val layers = (0, Integer.MAX_VALUE)
  
  private val pane = new ScalesPanel
  frame.getContentPane.add(pane)
  
  def act() {
    startTime = System.currentTimeMillis
    StepActor(step, this, startTime, context, layers)
    
    while (!(pq.isEmpty && done)) {
      receive {
        case r @ Request(node, time, message) => {
          pq += r
          timer.schedule(pq.max)
        }
        
        case AddPanel(panel) => {
          pane.add(panel)
          if (frame.isVisible) {
            pane.repaint()
          } else {
            frame.setVisible(true)
          }
        }
        
        case RemovePanel(panel) => {
          pane.remove(panel)
        }
        
        case Notify => {
          while (!pq.isEmpty && pq.max.time <= System.currentTimeMillis) {
            val r = pq.dequeue
            r.node ! r.message
          }
          
          if (frame.isVisible) pane.repaint()
          if (!pq.isEmpty) timer.schedule(pq.max)
        }
      
        case Done => {
          done = true
        }
      }
    }
    
    finish()
  }
  
  private def finish() {
    synth.close()
    frame.setVisible(false)
    System.exit(0)
  }
}
