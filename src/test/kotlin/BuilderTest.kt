import main.kotlin.Component
import org.junit.Test

class BuilderTest {
    @Test
    fun builderTest() {
        val component = Component.Builder()
            .setParam1("Some value")
            .setParam3(true)
            .build()
        println(component)
        println(component.param1)
        println(component.param3)

        assert(component.param1 == "Some value")
        assert(component.param3 == true)
        assert(component.param2 == null)

    }
}