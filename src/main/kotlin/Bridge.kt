package main.kotlin

interface Shape {
    fun renderShape(): String
}

interface Color {
    fun renderColorShape(): String
}

class ShapeImpl(private val shapeName: String) : Shape {
    override fun renderShape(): String = shapeName
}

// Bridge via - constructor
class ColorShapeImpl(private val color: String, private val shape: Shape) : Color {
    override fun renderColorShape(): String = "$color ${shape.renderShape()}"
}