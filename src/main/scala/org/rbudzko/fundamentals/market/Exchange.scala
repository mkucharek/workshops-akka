package org.rbudzko.fundamentals.market


import akka.actor.{Actor, ActorRef, PoisonPill}
import akka.event.Logging
import org.rbudzko.fundamentals.merchant.{Give, Transfer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Living exchange playing role of mediator between buyers.
 */
class Exchange(seller: ActorRef, good: Good) extends Actor {

  val log = Logging(context.system, this)
  val hourglass = context.system.scheduler.scheduleOnce(20 milliseconds, context.self, TimeUp)
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
  def bid(buyer: ActorRef, gold: Long) = {
    var watching = List(buyer)
    log.info("Bid for [{}] from [{}] on [{}]", good, sender().path.name, gold)
    /**
     * Should finalize transaction.
     */
    if (offer.getOrElse(0L) < gold) {
      if (winner.isDefined) {
        watching = winner.get :: watching
      }

      offer = Option(gold)
      winner = Option(buyer)
    } else {
      log.info("Late bid from [{}]", sender())
    }

    watching.foreach(_ ! Description(good, offer, winner))
  }

  def exchange() = {
    if (winner.isDefined) {
      winner.foreach(_ ! Transfer(Math.negateExact(offer.getOrElse(0L))))
      winner.foreach(_ ! Give(good))

      seller ! Transfer(offer.getOrElse(0L))
    } else {
      seller ! Give(good)
    }

    self ! PoisonPill
  }

  override def postStop() = hourglass.cancel()
}

/**
 * Exchange commands.
 */

case class Bid(gold: Long)

case class Description(good: Good, gold: Option[Long], winner: Option[ActorRef])

object AskForDescription

object TimeUp
