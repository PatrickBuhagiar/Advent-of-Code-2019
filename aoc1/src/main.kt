import java.io.File
import kotlin.math.floor

fun main() {
    val numbers = readLines("src/input.txt")
    val sum = numbers.map { process(it) }.sum()
    println(sum)
}

fun readLines(fileName: String): List<Double> =
    File(fileName).useLines { it.map { s -> s.toDouble() }.toList() }

fun process(mass: Double, total:Double=0.0): Double {
    val fuel = floor(mass / 3.0) - 2.0
    return if (fuel <= 0) total else process(fuel, total + fuel)
}