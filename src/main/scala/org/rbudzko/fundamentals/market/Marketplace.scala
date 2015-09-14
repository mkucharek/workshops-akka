package org.rbudzko.fundamentals.market

import akka.actor.{Terminated, Props, Actor, ActorRef}
import akka.event.Logging
import org.rbudzko.fundamentals.merchant.{CommonMerchant, SlaveMerchant}

/**
 * Marketplace spawns new merchants and broadcasts new offers to all of them.
 */

class Marketplace extends Actor {

  val log = Logging(context.system, this)
  var participants = List.empty[ActorRef]

  /**
   * Should create merchants to work with.
   */
  override def preStart() = {
    participants = List.apply(
      context.actorOf(Props(classOf[SlaveMerchant], 100L, List(Slave(15), Slave(31), Cow(2)), self), "Guid"),
      context.actorOf(Props(classOf[SlaveMerchant], 30L, List(Slave(23), Slave(15)), self), "Sam"),
      context.actorOf(Props(classOf[CommonMerchant], 20L, List(), self), "Sylva"),
      context.actorOf(Props(classOf[CommonMerchant], 50L, List(Slave(31), Cow(3), Cow(1)), self), "Ariel"),
      context.actorOf(Props(classOf[CommonMerchant], 50L, List(Slave(1), Cow(4)), self), "Dod")
    )

    participants.foreach(context.watch)
  }

  override def receive = {
    case OfferTransaction(transaction) => broadcast(transaction)
    case Terminated(merchant) => ???
    case _ => unhandled _
  }

  /**
   * Should broadcast transaction to all participants but sender.
   */
  def broadcast(transaction: ActorRef) = {
    log.info("Telling merchants about new offer.")
    participants
      .filterNot(_ eq sender())
      .foreach(_ ! OfferTransaction(transaction))
  }
}

/**
 * Marketplace commands.
 */

case class OfferTransaction(transaction: ActorRef)

/**
 * Goods possible to trade.
 */

sealed trait Good {}

case class Cow(age: Int) extends Good

case class Slave(age: Int) extends Good
