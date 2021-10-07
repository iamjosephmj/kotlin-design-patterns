package main.kotlin

sealed class Country

object Spain : Country()
object Greece : Country()
object USA : Country()
object Poland : Country()
object Canada : Country()

class Currency(val code: String)

object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Canada -> Currency("CAD")
            is Poland -> Currency("PLN")
        }
}