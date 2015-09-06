package org.rbudzko.fundamentals.merchant

import akka.actor.ActorRef
import org.rbudzko.fundamentals.market.{Bid, Good, Slave}

/**
 * Merchant interested in slaves only.
 */

class SlaveMerchant(initialGold: Long, initialItems: List[Good], marketplace: ActorRef) extends Merchant(initialGold, initialItems, marketplace) {
  override def evaluate(good: Good, price: Option[Long], winner: Option[ActorRef]) = {
    if (winner.isEmpty || !self.eq(winner.get))
      good match {
        case Slave(name, age) => if (price.getOrElse(0L) < 40L - age) sender() ! Bid(price.getOrElse(0L) + 1L)
        case _ =>
      }
  }
}
