import main.kotlin.Scoop
import main.kotlin.StoreVisitorA
import main.kotlin.StoreVisitorB
import main.kotlin.StoreVisitorC
import org.junit.Test

class VisitorTest {
    @Test
    fun testVisitor() {
        val visitorA = StoreVisitorA(5)
        val visitorB = StoreVisitorB(8)
        val visitorC = StoreVisitorC(10)

        assert(visitorA.visit().scoopType == Scoop.Small)
        assert(visitorB.visit().scoopType == Scoop.Medium)
        assert(visitorC.visit().scoopType == Scoop.Large)

    }
}