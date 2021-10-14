package main.kotlin

import org.junit.Test

// Skeletons
interface TargetInterface {
    fun call(limit: Int): List<Int>
}

interface AdapteeInterface {
    fun specificCall(data: List<String>): String
}

interface TargetAdapteeConverter {
    fun convertTargetToAdaptee(data: List<Int>): List<String>
}

// Implementations
class Target : TargetInterface {
    override fun call(limit: Int): List<Int> = (0..limit).toList()
}

class Adaptee : AdapteeInterface {
    override fun specificCall(data: List<String>): String = data.map { it }.reduce { acc, s -> "$acc$s" }
}

// Adapter
class Adapter : TargetAdapteeConverter {
    override fun convertTargetToAdaptee(data: List<Int>): List<String> = data.map { it.toString() }
}

class AdapterTest {
    @Test
    fun adapterTest() {

        val adapter = Adapter()
        val target = Target()
        val adaptee = Adaptee()

        val intData = target.call(3)

        val adapterResult = adapter.convertTargetToAdaptee(intData)

        val result = adaptee.specificCall(adapterResult)

        assert(result == "0123")
    }
}