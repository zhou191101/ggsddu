package com.ggsddu.currency

abstract class CurrencyZone {
  type Currency <: AbstractCurrency

  def make(x: Long): Currency

  val CurrencyUnit: Currency

  abstract class AbstractCurrency {
    val amount: Long

    def designation: String

    override def toString: String = ((amount.toDouble / CurrencyUnit.amount.toDouble)
      formatted ("%." + decimals(CurrencyUnit.amount) + "f") + designation)

    def +(that: Currency): Currency = make(this.amount + that.amount)

    def *(x: Double): Currency = make((this.amount * x).toLong)

    def -(that: Currency): Currency = make(this.amount - that.amount)

    def /(that: Double) = this.amount / that

    def /(that: Currency): Currency = make(this.amount / that.amount)

    def from(other: CurrencyZone#AbstractCurrency): Currency =
      make(math.round(
        other.amount.toDouble * Converter.exchangeRate(other.designation)(this.designation)
      ))

    private def decimals(n: Long): Int = {
      if (n == 1) 0 else 1 + decimals(n / 10)
    }
  }


}
