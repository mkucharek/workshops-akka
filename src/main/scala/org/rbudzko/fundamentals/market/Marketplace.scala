package org.rbudzko.fundamentals.market

import akka.actor.{Actor, ActorRef}

/**
 * Marketplace spawns new merchants and broadcasts new offers to all of them.
 */

class Marketplace(participants: List[ActorRef]) extends Actor {

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

case class Slave(name: String, age: Int) extends Good
