package org.rbudzko.fundamentals.training

import java.net.URL

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging
import org.slf4j.LoggerFactory

object ProtectingState extends App {
  val log = LoggerFactory.getLogger(ProtectingState.getClass)
  val system = ActorSystem()
  val scribe = system.actorOf(Props[CountingScribe], "CountingScribe")

  log.info("Issuing messages.")

  scribe ! "Hello!"
  scribe ! "A little bit longer sentence."
  scribe ! 12L
  scribe ! new URL("http://google.pl")
  scribe ! "White rabbit..."

  log.info("Messages issued.")
}

class CountingScribe extends Actor {
  val log = Logging(context.system, this)
  var counted = 0L

  override def receive = {
    case sentence: String =>
      counted += sentence.length
      log.info("Received sentence [{}] and it's length is [{}]. Counted [{}] so far.", sentence, sentence.length, counted)
    case any: Any => throw new IllegalArgumentException()
  }
}
