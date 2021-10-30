import main.kotlin.*
import org.junit.Test

class CompositeTest {

    @Test
    fun compositeTest() {
        val computer = Computer()
        val cabinet = Cabinet()
        val other = Others()

        val processor = Processor()
        val ram = Ram()
        val graphicsCard = GraphicsCard()
        val otherComponent = OtherCpuComponents()

        val monitor = Monitor()
        val keyboard = Keyboard()
        val mouse = Mouse()
        val popToy = PopToy()

        other.addEquipment(monitor)
        other.addEquipment(keyboard)
        other.addEquipment(mouse)
        other.addEquipment(popToy)

        cabinet.addEquipment(processor)
        cabinet.addEquipment(ram)
        cabinet.addEquipment(graphicsCard)
        cabinet.addEquipment(otherComponent)

        computer.addComponent(cabinet)
        computer.addComponent(other)

        assert(computer.price == 5310)

    }

    @Test
    fun compositeTestWithTwoPopToy() {
        val computer = Computer()
        val cabinet = Cabinet()
        val other = Others()

        val processor = Processor()
        val ram = Ram()
        val graphicsCard = GraphicsCard()
        val otherComponent = OtherCpuComponents()

        val monitor = Monitor()
        val keyboard = Keyboard()
        val mouse = Mouse()
        val popToy = PopToy()

        other.addEquipment(monitor)
        other.addEquipment(keyboard)
        other.addEquipment(mouse)
        other.addEquipment(popToy)

        // bought one more pop toy
        other.addEquipment(popToy)

        cabinet.addEquipment(processor)
        cabinet.addEquipment(ram)
        cabinet.addEquipment(graphicsCard)
        cabinet.addEquipment(otherComponent)

        computer.addComponent(cabinet)
        computer.addComponent(other)

        assert(computer.price == 5320)

    }
}