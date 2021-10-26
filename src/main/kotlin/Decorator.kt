package main.kotlin

interface CoffeeMachine {
    // Functionality 1
    fun getHotWater()

    // Functionality 2
    fun makeCoffee()

}

class CF : CoffeeMachine {

    override fun getHotWater() {
        print("hot water")
    }

    override fun makeCoffee() {
        print("coffee")
    }

}

class BrettCoffeeMachine(private val machine: CoffeeMachine) : CoffeeMachine by machine {

    override fun makeCoffee() {
        print("add Colombian flavour")
        machine.makeCoffee()
    }
}
