import java.io.File

fun main() {
    for (noun in 0..100) {
        for (verb in 0..100) {
            val codeSequence = getCode()
            codeSequence[1] = noun
            codeSequence[2] = verb
            process(codeSequence)
            if (codeSequence[0] == 19690720) {
                println(100 * codeSequence[1] + codeSequence[2])
                break;
            }
        }
    }

}

private fun process(codeSequence: MutableList<Int>) {
    var currentIndex = 0
    var currentValue = codeSequence[currentIndex]

    while (currentValue != 99) {
        val value1Index = codeSequence[currentIndex + 1]
        val value2Index = codeSequence[currentIndex + 2]
        val resultIndex = codeSequence[currentIndex + 3]

        val value1 = codeSequence[value1Index]
        val value2 = codeSequence[value2Index]

        if (currentValue == 1) {
            //add opp
            codeSequence[resultIndex] = value1 + value2
        } else if (currentValue == 2) {
            // multiply
            codeSequence[resultIndex] = value1 * value2
        } else {
            break;
        }

        currentIndex += 4
        currentValue = codeSequence[currentIndex]
    }
}

private fun getCode(): MutableList<Int> {
    val readText = File("src/input.csv").readText();
    return readText
        .trim()
        .splitToSequence(",")
        .toList()
        .map { s -> s.toInt() }.toMutableList()
}