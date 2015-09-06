package org.rbudzko.fundamentals.market


import akka.actor.{Actor, ActorRef}
import akka.event.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Living exchange playing role of mediator between buyers.
 */
class Exchange(seller: ActorRef, good: Good) extends Actor {

  val log = Logging(context.system, this)
  val hourglass = context.system.scheduler.scheduleOnce(1 seconds, context.self, TimeUp)
  var winner = Option.empty[ActorRef]
  var offer = Option.empty[Long]

  override def receive = {
    case Bid(gold) => bid(sender(), gold)
    case AskForDescription => sender() ! Description(good, offer, winner)
    case TimeUp =>
      exchange()
    case _ => unhandled _
  }

  def bid(buyer: ActorRef, gold: Long) = ???

  def exchange() = ???

  override def postStop() = hourglass.cancel()
}

/**
 * Exchange commands.
 */

case class Bid(gold: Long)

case class Description(good: Good, gold: Option[Long], winner: Option[ActorRef])

object AskForDescription

object TimeUp
