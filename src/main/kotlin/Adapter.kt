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
    fun convertTargetToAdaptee(limit: Int): List<String>
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
    override fun convertTargetToAdaptee(limit: Int): List<String> {
        // Mappings are done here.
        val target = Target()
        return target.call(limit).map { it.toString() }
    }
}
