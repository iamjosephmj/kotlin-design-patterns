import main.kotlin.ColorShapeImpl
import main.kotlin.Shape
import main.kotlin.ShapeImpl
import org.junit.Test

class BridgeTest {

    @Test
    fun bridgeTest() {

        val rectangle: Shape = ShapeImpl(shapeName = "Rectangle")

        val redRectangle = ColorShapeImpl(color = "Red", rectangle)

        assert("Red Rectangle" == redRectangle.renderColorShape())

    }
}