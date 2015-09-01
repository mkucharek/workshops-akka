package org.rbudzko.fundamentals.merchant

import akka.actor.ActorRef
import org.rbudzko.fundamentals.market.{Bid, Good, Slave}

/**
 * Merchant interested in slaves only.
 */

class SlaveMerchant(initialGold: Long, initialItems: List[Good], marketplace: ActorRef) extends Merchant(initialGold, initialItems, marketplace) {
  override def evaluate(good: Good, price: Long) = {
    case Slave(name, age) => if (price < 40 - age) sender() ! Bid(price + 1)
    case _ => unhandled _
  }
}
