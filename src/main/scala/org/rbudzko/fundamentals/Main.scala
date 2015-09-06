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

  system.actorOf(Props[Marketplace], "Marketplace")

  def awareness() = {
    Math.abs(random.nextInt()) % 100 < 75
  }

  def index() = {
    number.getAndIncrement()
  }
}
