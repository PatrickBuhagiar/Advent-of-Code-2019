import java.io.File

fun main() {
    val code = getCode();
    print(process(code, 5));
}

private fun process(codeSequence: MutableList<Int>, input: Int): Int {
    var currentIndex = 0
    var output = 0;
    var currentValue = codeSequence[currentIndex]
    while (currentValue != 99) {
        val parameters = parseParameterMode(currentValue)
        val opCode = parameters[0]
        when {
            opCode == 1 -> {
                //add opp
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val resultIndex = codeSequence[currentIndex + 3]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                codeSequence[resultIndex] = v1 + v2
                currentIndex += 4
            }
            opCode == 2 -> {
                // multiply
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val resultIndex = codeSequence[currentIndex + 3]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                codeSequence[resultIndex] = v1 * v2
                currentIndex += 4
            }
            opCode == 3 -> {
                // save input
                val value1Index = codeSequence[currentIndex + 1]
                codeSequence[value1Index] = input
                currentIndex += 2
            }
            opCode == 4 -> {
                // save output
                val value1Index = codeSequence[currentIndex + 1]
                output = codeSequence[value1Index]
                currentIndex += 2
            }
            opCode == 5 -> {
                // jump if true
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                if (v1 != 0) {
                    currentIndex = v2
                } else {
                    currentIndex += 3
                }
            }
            opCode == 6 -> {
                // jump if false
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                if (v1 == 0) {
                    currentIndex = v2
                } else {
                    currentIndex += 3
                }
            }
            opCode == 7 -> {
                // less than
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val resultIndex = codeSequence[currentIndex + 3]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                if (v1 < v2) {
                    codeSequence[resultIndex] = 1
                } else {
                    codeSequence[resultIndex] = 0
                }
                currentIndex += 4
            }
            opCode == 8 -> {
                // greater than
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val resultIndex = codeSequence[currentIndex + 3]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index

                if (v1 == v2) {
                    codeSequence[resultIndex] = 1
                } else {
                    codeSequence[resultIndex] = 0
                }
                currentIndex += 4
            }
        }

        currentValue = codeSequence[currentIndex]
    }
    return output
}

private fun parseParameterMode(currentValue: Int): List<Int> {
    val s = currentValue.toString()
    when (s.length) {
        1, 2 -> return listOf(currentValue, 0, 0, 0)
        3 -> {
            val opCode = s.substring(1)
            return listOf(opCode.toInt(), s[0].toString().toInt(), 0, 0)
        }
        4 -> {
            val opCode = s.substring(2)
            return listOf(opCode.toInt(), s[1].toString().toInt(), s[0].toString().toInt(), 0)
        }
        5 -> {
            val opCode = s.substring(3)
            return listOf(opCode.toInt(), s[2].toString().toInt(), s[1].toString().toInt(), s[0].toString().toInt())
        }
    }
    return arrayListOf(0);
}

private fun getCode(): MutableList<Int> {
    val readText = File("src/input.csv").readText();
    return readText
        .trim()
        .splitToSequence(",")
        .toList()
        .map { s -> s.toInt() }.toMutableList()
}