import kotlin.math.abs

class Cell(private val x: Int, private val y: Int) {
    val distance: Int = abs(0-x) + abs(0-y)
    var wires: MutableList<Wire> = mutableListOf()
    var totalSteps = 0
    
    fun calculateSteps(): Int {
        totalSteps = wires.sumBy { wire -> wire.steps }
        return totalSteps
    }
}