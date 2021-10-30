package main.kotlin

interface Image {
    fun display()
}

class RealImage(private val filename: String) : Image {
    override fun display() {
        println("RealImage: Displaying $filename")
    }

    private fun loadFromDisk(filename: String) {
        println("RealImage: Loading $filename")
    }

    init {
        loadFromDisk(filename)
    }
}

class ProxyImage(private val filename: String) : Image {
    private var realImage: RealImage? = null

    override fun display() {
        println("ProxyImage: Displaying $filename")
        if (realImage == null) {
            realImage = RealImage(filename)
        }
        realImage!!.display()
    }
}
