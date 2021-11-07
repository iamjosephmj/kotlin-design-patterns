import main.kotlin.EventGenerator
import main.kotlin.EmailNotificationListener
import main.kotlin.LogOpenListener
import org.junit.Test

class ObserverTest {
    @Test
    fun testObserver() {
        val generator = EventGenerator()

        val logListener = LogOpenListener("path/to/log/file.txt")
        val emailListener = EmailNotificationListener("test@test.com")

        generator.events.subscribe("a", logListener)
        generator.events.subscribe("a", emailListener)
        generator.events.subscribe("b", emailListener)

        generator.generateEventA("test.txt")
        generator.generateEventB()
    }
}