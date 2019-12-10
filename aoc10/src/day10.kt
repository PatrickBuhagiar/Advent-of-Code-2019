import java.io.File
import kotlin.math.atan2

val asteroids = mutableListOf<Asteroid>()

fun main() {
    val lines = readLines("src/input.txt")
    val maxX = lines[0].length
    val maxY = lines.size
    // populating asteroids
    for (x in 0 until maxX) {
        for (y in 0 until maxY) {
            val c: Char = lines[y][x]
            if (c == '#') {
                asteroids.add(Asteroid(x, y))
            }
        }
    }

    // part 1
    val best = asteroids.map { xAsteroid ->
        Pair(xAsteroid, asteroids
            .filter { it.key() != xAsteroid.key() }
            .map { yAsteroid -> atan2((xAsteroid.x - yAsteroid.x).toFloat(), (xAsteroid.y - yAsteroid.y).toFloat()) }
            .distinct()
            .count())
    }.maxBy { it.second }
    println("Asteroid at ${best!!.first.x},${best.first.y} with # of asteroids ${best.second}")

    // part 2
    val bestAsteroid = best.first
    val targetMap = mutableMapOf<Float, MutableList<Asteroid>>()
    asteroids
        .filter { it.key() != bestAsteroid.key() }
        .forEach { targetAsteroid ->
            val angle = atan2((bestAsteroid.x - targetAsteroid.x).toFloat(), (bestAsteroid.y - targetAsteroid.y).toFloat())
            if (targetMap.contains(angle)) {
                targetMap[angle]!!.add(targetAsteroid)
            } else {
                targetMap[angle] = mutableListOf(targetAsteroid)
            }
        }

    // Figure out Rotation order. 0 rad is upwards, left is negative, right is postiive 
    val pastZero = targetMap.keys.filter { it <= 0 }.sortedDescending()
    val preZero = targetMap.keys.filter { it > 0 }.sortedDescending()

    val orderedClockwise = mutableListOf<Float>()
    orderedClockwise.addAll(pastZero)
    orderedClockwise.addAll(preZero)

    //converted targetMapValues into Iterable
    val iterableMap = mutableMapOf<Float, MutableIterator<Asteroid>>()
    targetMap.forEach() { iterableMap[it.key] = it.value.iterator() }

    var hit = 0
    do {
        for (i in 0 until 296) {
            val iterable = iterableMap[orderedClockwise[i]]
            if (iterable!!.hasNext()) {
                val next = iterable.next()
                hit++
                if (hit == 200) {
                    println("200th asteroid is at ${next.x},${next.y}")
                    println(next.x * 100 + next.y)
                }
            }

        }
    } while (hit < 202)
}

fun readLines(fileName: String) = File(fileName).readLines()
fun key(x: Int, y: Int): String = "$x,$y"
