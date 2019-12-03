import kotlin.math.abs

class Cell(private val x: Int, private val y: Int) {
    val distance: Int = abs(0-x) + abs(0-y)
    var wires: MutableSet<Int> = mutableSetOf();
}