package org.rbudzko.fundamentals.merchant

import akka.actor.ActorRef
import org.rbudzko.fundamentals.Main
import org.rbudzko.fundamentals.market.{Bid, Good}

/**
 * Merchant interested in all types of goods.
 */

class CommonMerchant(initialGold: Long, initialItems: List[Good], marketplace: ActorRef) extends Merchant(initialGold, initialItems, marketplace) {
  /**
   * Should trade somehow.
   */
  override def evaluate(good: Good, price: Option[Long], winner: Option[ActorRef]) = {
    if ((winner.isEmpty || !self.eq(winner.get)) && Main.awareness() && price.getOrElse(0L) < 15) {
      val bid = Bid(price.getOrElse(0L) + 3L)
      log.info("I'm interested in [{}]! Will bid [{}].", good, bid.gold)
      sender() ! bid
    }
  }
}
