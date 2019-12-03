import java.io.File

var y: Int = 0
var x: Int = 0
var currentWire: Int = 0
val cells = mutableMapOf("0,0" to Cell(0, 0))
var stepCounter = 0

fun main() {
    val start = System.currentTimeMillis()
    val wires = readLines("src/input.txt")
    wires.forEach { wireInstructions ->
        x = 0
        y = 0
        stepCounter = 0

        wireInstructions.forEach {
            addWire(it)
        }
        currentWire += 1
    }

    val closestIntersection = cells.values.toList().filter { it.wires.size > 1 }.minBy { x -> x.distance }!!.distance
    println("closest Intersection: $closestIntersection")

    val fewestSteps = cells.values.toList()
        .filter { it.wires.size > 1 }.minBy { cell -> cell.calculateSteps() }!!.totalSteps

    println("fewest steps $fewestSteps")
    val timeTaken = System.currentTimeMillis() - start
    println(timeTaken / 1000.0)
}

private fun addWire(wireInstruction: WireInstruction) {
    for (i in 1..wireInstruction.steps) {
        stepCounter += 1
        when (wireInstruction.direction) {
            'U' -> y += 1
            'D' -> y -= 1
            'L' -> x -= 1
            'R' -> x += 1
        }

        if (!cells.containsKey(key(x, y))) {
            cells[key(x, y)] = Cell(x, y)
        }
        if (!cells.getValue(key(x, y)).wires.filter { w -> w.id == currentWire }.any()) {
            cells.getValue(key(x, y)).wires.add(Wire(currentWire, stepCounter))
        }
    }
}

fun key(x: Int, y: Int): String = "$x,$y"

fun readLines(fileName: String) =
    File(fileName).useLines {
        it.map { wireInstructions ->
            wireInstructions.trim()
                .splitToSequence(",").toList()
                .map { instruction: String ->
                    WireInstruction(
                        instruction[0],
                        instruction.drop(1).toInt()
                    )
                }
        }.toList()

    }