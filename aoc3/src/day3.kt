import java.io.File

var y: Int = 0
var x: Int = 0
var wire: Int = 0
val cells = mutableMapOf("0,0" to Cell(0, 0))

fun main() {
    val wires = readLines("src/input.txt")
    wires.forEach { wireInstructions ->
        run {
            x = 0
            y = 0

            wireInstructions.forEach {
                when (it.direction) {
                    Direction.UP -> goUp(it)
                    Direction.DOWN -> goDown(it)
                    Direction.LEFT -> goLeft(it)
                    Direction.RIGHT -> goRight(it)
                }
            }
            wire += 1
        }
    }
    val closestIntersection = cells.values.toList().filter { it.wires.size > 1 }.sortedBy { x -> x.distance }.first().distance
    print("closest Intersection: $closestIntersection")
}

fun goUp(it: WireInstruction) {
    for (i in 1..it.steps) {
        y += 1
        if (!cells.containsKey(key(x, y))) {
            cells[key(x, y)] = Cell(x, y)
        }
        cells.getValue(key(x, y)).wires.add(wire)
    }
}

fun goDown(it: WireInstruction) {
    for (i in 1..it.steps) {
        y -= 1
        if (!cells.containsKey(key(x, y))) {
            cells[key(x, y)] = Cell(x, y)
        }
        cells.getValue(key(x, y)).wires.add(wire)
    }
}

fun goLeft(it: WireInstruction) {
    for (i in 1..it.steps) {
        x -= 1
        if (!cells.containsKey(key(x, y))) {
            cells[key(x, y)] = Cell(x, y)
        }
        cells.getValue(key(x, y)).wires.add(wire)
    }
}


fun goRight(it: WireInstruction) {
    for (i in 1..it.steps) {
        x += 1
        if (!cells.containsKey(key(x, y))) {
            cells[key(x, y)] = Cell(x, y)
        }
        cells.getValue(key(x, y)).wires.add(wire)
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
                        getDirection(instruction[0]),
                        instruction.drop(1).toInt()
                    )
                }
        }.toList()

    }

fun getDirection(c: Char): Direction {
    return when (c) {
        'U' -> {
            Direction.UP
        }
        'D' -> {
            Direction.DOWN
        }
        'L' -> {
            Direction.LEFT
        }
        else -> {
            Direction.RIGHT
        }
    }
}