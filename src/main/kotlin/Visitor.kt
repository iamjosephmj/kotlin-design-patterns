package main.kotlin

sealed class Scoop {
    object Small : Scoop()
    object Medium : Scoop()
    object Large : Scoop()
}

interface Store {
    fun <R> deliver(visitor: StoreVisitor<R>): R
}


data class VanillaIceCream(val scoopType: Scoop) : Store {
    override fun <R> deliver(visitor: StoreVisitor<R>): R = visitor.visit()
}

data class BlueBerryIceCream(val scoopType: Scoop) : Store {
    override fun <R> deliver(visitor: StoreVisitor<R>): R = visitor.visit()
}

data class SpanishDelightIceCream(val scoopType: Scoop) : Store {
    override fun <R> deliver(visitor: StoreVisitor<R>): R = visitor.visit()
}


interface StoreVisitor<out R> {
    fun visit(): R
}


class StoreVisitorA(private val contract: Long) : StoreVisitor<VanillaIceCream> {
    override fun visit(): VanillaIceCream {
        return when (contract) {
            in 1..5 -> {
                VanillaIceCream(Scoop.Small)
            }
            in 5..10 -> {
                VanillaIceCream(Scoop.Medium)
            }
            else -> {
                VanillaIceCream(Scoop.Large)
            }
        }
    }
}

class StoreVisitorB(private val contract: Long) : StoreVisitor<BlueBerryIceCream> {
    override fun visit(): BlueBerryIceCream {
        return when (contract) {
            in 1..7 -> {
                BlueBerryIceCream(Scoop.Small)
            }
            in 7..20 -> {
                BlueBerryIceCream(Scoop.Medium)
            }
            else -> {
                BlueBerryIceCream(Scoop.Large)
            }
        }
    }
}

class StoreVisitorC(private val contract: Long) : StoreVisitor<SpanishDelightIceCream> {
    override fun visit(): SpanishDelightIceCream {
        return when (contract) {
            in 1..2 -> {
                SpanishDelightIceCream(Scoop.Small)
            }
            in 2..7 -> {
                SpanishDelightIceCream(Scoop.Medium)
            }
            else -> {
                SpanishDelightIceCream(Scoop.Large)
            }
        }
    }
}
