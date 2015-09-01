package org.rbudzko.fundamentals.merchant

import java.util.UUID

import akka.actor.{Actor, ActorRef, Props}
import org.rbudzko.fundamentals.market._

import scala.concurrent.duration._

/**
 * Merchant base to inherit from.
 */

abstract class Merchant(var gold: Long, var items: List[Good], val marketplace: ActorRef) extends Actor {

  val instinct = context.system.scheduler.schedule(3 seconds, 3 seconds, context.self, TimeToTrade)

  def evaluate(good: Good, price: Long)

  override def receive = {
    case OfferTransaction(transaction) => transaction ! AskForDescription
    case Description(good, price) => evaluate(good, price)
    case Transfer(amount) => gold = gold + amount
    case Give(good) => items = good :: items
    case TimeToTrade =>
      marketplace ! OfferTransaction(
        context.actorOf(
          Props(classOf[Exchange], self, items.head),
          "transaction-" + self.path.name + "-" + UUID.randomUUID()))
      items = items.drop(1)
  }

  override def postStop() = instinct.cancel()
}

/**
 * Merchant commands.
 */

case class Give(good: Good)

case class Transfer(gold: Long)

object TimeToTrade
