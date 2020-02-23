package com.ggsddu.currency

object Europe extends CurrencyZone {

  abstract class Euro extends AbstractCurrency {
    override def designation: String = "EUR"
  }

  override type Currency = Euro

  override def make(x: Long) = new Euro {
    override val amount: Long = x
  }

  val Cent = make(1)
  val Euro = make(100)

  override val CurrencyUnit = Euro
}
