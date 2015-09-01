package org.rbudzko.fundamentals.market

import akka.actor.{Actor, ActorRef}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Marketplace spawns new merchants and broadcasts new offers to all of them.
 */

class Marketplace extends Actor {

  val society = context.system.scheduler.schedule(1 second, 10 seconds, context.self, MerchantSpawn)
  var participants = List.empty[ActorRef]

  override def receive = {
    case OfferTransaction(transaction) => broadcast(transaction)
    case MerchantSpawn => spawn()
    case _ => unhandled _
  }

  def spawn() = ???

  def broadcast(transaction: ActorRef) = ???

  override def postStop() = society.cancel()
}

/**
 * Marketplace commands.
 */

object MerchantSpawn

case class OfferTransaction(transaction: ActorRef)

/**
 * Goods possible to trade.
 */

sealed trait Good {}

case class Cow(age: Int) extends Good

case class Slave(name: String, age: Int) extends Good
