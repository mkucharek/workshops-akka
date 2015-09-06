package org.rbudzko.fundamentals.market

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import org.rbudzko.fundamentals.merchant.SlaveMerchant

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
      ???
    )
  }

  override def receive = {
    case OfferTransaction(transaction) => broadcast(transaction)
    case _ => unhandled _
  }

  /**
   * Should broadcast transaction to all participants but sender.
   */
  def broadcast(transaction: ActorRef) = ???
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
