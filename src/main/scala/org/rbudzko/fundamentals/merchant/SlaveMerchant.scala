package org.rbudzko.fundamentals.merchant

import akka.actor.ActorRef
import org.rbudzko.fundamentals.Main
import org.rbudzko.fundamentals.market.{Bid, Good, Slave}

/**
 * Merchant interested in slaves only.
 */

class SlaveMerchant(initialGold: Long, initialItems: List[Good], marketplace: ActorRef) extends Merchant(initialGold, initialItems, marketplace) {
  override def evaluate(good: Good, price: Option[Long], winner: Option[ActorRef]) = {
    if ((winner.isEmpty || !self.eq(winner.get)) && Main.awareness()) {
      good match {
        case Slave(age) =>
          if (price.getOrElse(0L) < 40L - age) {
            val bid = Bid(price.getOrElse(0L) + 1L)
            log.info("I'm interested in [{}]! Will bid [{}].", good, bid.gold)
            sender() ! bid
          } else {
            log.debug("Nah, this slave is too expensive.")
          }
        case _ =>
          log.debug("Nah, it's not a slave.")
      }
    }
  }
}
