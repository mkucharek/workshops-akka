package org.rbudzko.fundamentals.merchant

import akka.actor.ActorRef
import org.rbudzko.fundamentals.market.Good

/**
 * Merchant interested in all types of goods.
 */

class CommonMerchant(initialGold: Long, initialItems: List[Good], marketplace: ActorRef) extends Merchant(initialGold, initialItems, marketplace) {
  /**
   * Should trade somehow.
   */
  override def evaluate(good: Good, price: Option[Long], winner: Option[ActorRef]) = ???
}
