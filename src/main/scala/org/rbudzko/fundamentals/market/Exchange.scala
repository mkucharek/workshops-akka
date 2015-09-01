package org.rbudzko.fundamentals.market

import akka.actor.{Actor, ActorRef}

import scala.concurrent.duration._

/**
 * Living exchange playing role of mediator between buyers.
 */
class Exchange(seller: ActorRef, good: Good) extends Actor {

  val hourglass = context.system.scheduler.scheduleOnce(5 seconds, context.self, TimeUp)
  var winner = None[ActorRef]
  var offer = None[Long]

  val finalizing = {
    ???
  }

  override def receive = {
    case Bid(gold) => bid(sender(), gold)
    case AskForDescription => sender() ! Description(good, offer)
    case TimeUp => context.become(finalizing)
    case _ => unhandled _
  }

  def bid(buyer: ActorRef, gold: Long) = {
    ???
  }

  override def postStop() = hourglass.cancel()
}

/**
 * Exchange commands.
 */

case class Bid(gold: Long)

case class Description(good: Good, gold: Long)

object AskForDescription

object TimeUp
