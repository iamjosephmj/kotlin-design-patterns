import main.kotlin.Adaptee
import main.kotlin.Adapter
import org.junit.Test

class AdapterTest {
    @Test
    fun adapterTest() {

        val adapter = Adapter()
        val adaptee = Adaptee()

        val adapterResult = adapter.convertTargetToAdaptee(3)

        val result = adaptee.specificCall(adapterResult)

        assert(result == "0123")
    }
}