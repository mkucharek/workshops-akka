package org.rbudzko.fundamentals.merchant

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import akka.event.Logging
import org.rbudzko.fundamentals.Main
import org.rbudzko.fundamentals.market._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Merchant base to inherit from.
 */

abstract class Merchant(var gold: Long, var items: List[Good], val marketplace: ActorRef) extends Actor {

  val log = Logging(context.system, this)
  val instinct = context.system.scheduler.schedule(2 seconds, 10 seconds, context.self, TimeToTrade)

  def evaluate(good: Good, price: Option[Long], winner: Option[ActorRef])

  val depressed: Receive = {
    case any: Any => log.warning("I'm depressed, because I'll die in a moment, so I ignore [{}].", any)
  }

  override def receive = {
    case OfferTransaction(transaction) => transaction ! AskForDescription
    case Description(good, price, winner) => evaluate(good, price, winner)
    case Transfer(amount) =>
      log.debug("Receiving transfer [{}].", amount)
      gold = gold + amount
      log.info("New wallet [{}].", gold)
      if (gold < 0) {
        log.info("I've cheated - Seppuku!")
        self ! PoisonPill
        context.become(depressed)
      }
    case Give(good) =>
      log.debug("Receiving good [{}].", good)
      items = good :: items
      log.info("New list of items [{}].", items)
    case TimeToTrade =>
      if (items.nonEmpty && Main.awareness()) {
        log.info("Deciding to trade [{}].", items.head)
        marketplace ! OfferTransaction(
          context.actorOf(
            Props(classOf[Exchange], self, items.head),
            "transaction-" + self.path.name + "-" + Main.index()))
        items = items.drop(1)
        log.debug("New list of items [{}].", items)
      } else {
        log.debug("Want to trade, but no items to trade atm...")
      }
  }

  override def postStop() = instinct.cancel()
}

/**
 * Merchant commands.
 */

case class Give(good: Good)

case class Transfer(gold: Long)

object TimeToTrade
