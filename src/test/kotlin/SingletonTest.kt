import main.kotlin.NetworkDriver
import org.junit.Test

class SingletonTest {
    @Test
    fun testSingleton() {
        println("Start")
        val networkDriver1 = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        assert(networkDriver1 === networkDriver2)
    }
}