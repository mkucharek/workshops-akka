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
  val hourglass = context.system.scheduler.scheduleOnce(3 seconds, context.self, TimeUp)
  var winner = Option.empty[ActorRef]
  var offer = Option.empty[Long]

  override def receive = {
    case Bid(gold) => bid(sender(), gold)
    case AskForDescription => sender() ! Description(good, offer, winner)
    case TimeUp => exchange()
    case _ => unhandled _
  }

  /**
   * Should handle bid offer from merchant.
   */
  def bid(buyer: ActorRef, gold: Long) = ???

  /**
   * Should finalize transaction.
   */
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
