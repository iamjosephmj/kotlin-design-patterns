package main.kotlin

open class Equipment(
    open val price: Int,
    val name: String
)


open class Composite(name: String) : Equipment(0, name) {

    private val equipments: ArrayList<Equipment> = arrayListOf()
    private val composites: ArrayList<Composite> = arrayListOf()

    override val price: Int
        get() {

            val compositePrice = composites.sumOf { composite ->
                composite.price
            }

            return equipments.sumOf { it.price } + compositePrice
        }

    fun addEquipment(equipment: Equipment) = apply {
        equipments.add(equipment)
    }

    fun addComponent(composite: Composite) = apply {
        composites.add(composite)
    }
}


class Computer : Composite("Lucia's PC")

class Cabinet : Composite("GAMING CPU")

class Processor : Equipment(1000, "Lucia's Fav processor")

class Ram : Equipment(200, "Lucia's Fav Ram")

class GraphicsCard : Equipment(2000, "Lucia's Fav GPU")

class OtherCpuComponents : Equipment(1000, "Lucia's selected set of components for CPU")

class Others : Composite("Display and other input devices")

class Monitor : Equipment(700, "Lucia's Fav monitor with 144hz refresh rate")

class Keyboard : Equipment(200, "Lucia's Fav keyboard")

class Mouse : Equipment(200, "Lucia's Fav mouse")

class PopToy : Equipment(10, "Lucia's Fav pop toy to keep near her PC")


