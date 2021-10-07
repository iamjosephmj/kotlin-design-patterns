package main.kotlin

object NetworkDriver {
    init {
        println("Initializing: $this")
    }

    fun log(): NetworkDriver = apply { println("Network driver: $this") }
}