import java.io.File

fun main() {

    val padding = Array(1000) { 0L }
    val code = getCode()
    code.addAll(padding)
    val amp = Amp(code, mutableListOf(2L))
    amp.start()
}

private fun getCode(): MutableList<Long> {
    val readText = File("src/input.csv").readText()
    return readText
        .trim()
        .splitToSequence(",")
        .toList()
        .map { s -> s.toLong() }.toMutableList()
}

