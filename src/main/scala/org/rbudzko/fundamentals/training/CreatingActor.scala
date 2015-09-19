package org.rbudzko.fundamentals.training

import java.net.URL

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging
import org.slf4j.LoggerFactory

object CreatingActor extends App {
  val log = LoggerFactory.getLogger(CreatingActor.getClass)
  val system = ActorSystem()
  val scribe = system.actorOf(Props[Scribe], "Scribe")

  log.info("Issuing messages.")

  scribe ! "Hello!"
  scribe ! "A little bit longer sentence."
  scribe ! 12L
  scribe ! new URL("http://google.pl")

  log.info("Messages issued.")
}

class Scribe extends Actor {
  val log = Logging(context.system, this)

  override def receive = ???
}
