package main.kotlin

import org.junit.Test

sealed class Country

object Spain : Country()
class Greece(val someProperty: String) : Country()
data class USA(val someProperty: String) : Country()
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

class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val usaCurrency = CurrencyFactory.currencyForCountry(USA("")).code
        println("USA currency: $usaCurrency")

        val greekCurrency = CurrencyFactory.currencyForCountry(Greece("")).code
        println("Greek currency: $greekCurrency")

        assert(greekCurrency == "EUR")
        assert(usaCurrency == "USD")

    }
}