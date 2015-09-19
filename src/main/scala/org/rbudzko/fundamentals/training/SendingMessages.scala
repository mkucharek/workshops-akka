package org.rbudzko.fundamentals.training

import java.net.URL

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging
import org.slf4j.LoggerFactory

object SendingMessages extends App {
  val log = LoggerFactory.getLogger(SendingMessages.getClass)
  val system = ActorSystem()
  val scribe = system.actorOf(Props[MadScribe], "MadScribe")

  log.info("Issuing messages.")

  scribe ! "Hello!"
  scribe ! "A little bit longer sentence."
  scribe ! 12L
  scribe ! new URL("http://google.pl")
  scribe ! "White rabbit..."

  log.info("Messages issued.")
}

class MadScribe extends Actor {
  val log = Logging(context.system, this)
  var counted = 0L

  override def receive = ???
}
