import java.io.File

fun main() {
    val height = 6
    val width = 25
    val layersize = height * width
    val digits = loadDigits()

    val layers = mutableListOf<Layer>()

    for (i in digits.indices step layersize) {
        val subList = digits.subList(i, i + layersize)
        layers.add(Layer(subList, height, width))
    }

    val clone = layers.toMutableList()

    clone.sortBy { it.countZeros() }

    //part1
    println(clone.first().calculate())

    val pixelsAtIndex = mutableListOf<List<Int>>()

    for (i in 0 until layersize) {
        val pixelAtI: List<Int> = layers.map { it.pixels[i] }
        pixelsAtIndex.add(pixelAtI)
    }

    val merged = createLayerFrom(pixelsAtIndex)
    for (i in merged.indices step width) {
        val subList = merged.subList(i, i + width)
        val joinToString = subList.joinToString("")
        println(joinToString.replace("0", " ").replace("1", "#"))
    }
}

fun createLayerFrom(toMerge: MutableList<List<Int>>): List<Int> {
    return toMerge.map { figureOutPixel(it) }
}

fun figureOutPixel(pixels: List<Int>): Int {
    for (i in pixels.indices) {
        if (pixels[i] == 1 || pixels[i] == 0) {
            return pixels[i]
        }
    }
    return -1
}

fun loadDigits(): List<Int> {
    val readText = File("src/input.txt").readText()
    return readText.trim().toList().map { it.toString().toInt() }.toList()
}

