package org.rbudzko.fundamentals

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{ActorSystem, Props}
import org.rbudzko.fundamentals.market.Marketplace

import scala.util.Random

/**
 * Main class to setup actor system.
 */

object Main extends App {
  val number = new AtomicInteger(1)
  val system = ActorSystem()
  val random = new Random()
  val merchants = List.apply(???)

  system.actorOf(Props(classOf[Marketplace], merchants))

  def should() = {
    Math.abs(random.nextInt()) % 5 < 2
  }

  def index() = {
    number.getAndIncrement()
  }
}
