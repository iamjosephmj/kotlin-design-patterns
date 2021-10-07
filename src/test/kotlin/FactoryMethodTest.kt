import main.kotlin.CurrencyFactory
import main.kotlin.Greece
import main.kotlin.USA
import org.junit.Test

class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val usaCurrency = CurrencyFactory.currencyForCountry(USA).code
        println("USA currency: $usaCurrency")

        val greekCurrency = CurrencyFactory.currencyForCountry(Greece).code
        println("Greek currency: $greekCurrency")

        assert(greekCurrency == "EUR")
        assert(usaCurrency == "USD")

    }
}