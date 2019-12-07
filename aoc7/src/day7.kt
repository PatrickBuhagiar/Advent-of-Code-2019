import java.io.File
import java.util.*

fun main() {
    val code = getCode();
    val listOf = listOf(0, 1, 2, 3, 4)
    var perms = permute(listOf)
    val results = arrayListOf<Int>()

    //part 1
    perms.forEach() { perm ->
        println("$perm")
        var outputPerPerm = 0
        for (i in perm.indices) {
            outputPerPerm = process(code, listOf(perm[i], outputPerPerm))
        }
        println("trying perm $perm, value=$outputPerPerm")
        results.add(outputPerPerm)
    }
    println(results.max())
}

private fun process(codeSequence: MutableList<Int>, input: List<Int>): Int {
    var inputCounter = 0
    var currentIndex = 0
    var output = 0;
    var currentValue = codeSequence[currentIndex]
    var halt = false
    loop@ while (currentValue != 99) {
        val parameters = parseParameterMode(currentValue)
        when (parameters[0]) {
            1 -> {
                //add opp
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val resultIndex = codeSequence[currentIndex + 3]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                codeSequence[resultIndex] = v1 + v2
                currentIndex += 4
            }
            2 -> {
                // multiply
                val value1Index = codeSequence[currentIndex + 1]
                val value2Index = codeSequence[currentIndex + 2]
                val resultIndex = codeSequence[currentIndex + 3]
                val v1 = if (parameters[1] == 0) codeSequence[value1Index] else value1Index
                val v2 = if (parameters[2] == 0) codeSequence[value2Index] else value2Index
                codeSequence[resultIndex] = v1 * v2
                currentIndex += 4
            }
            3 -> {
                // save input
                val value1Index = codeSequence[currentIndex + 1]
                codeSequence[value1Index] = input[inputCounter]
                inputCounter += 1
                currentIndex += 2
            }
            4 -> {
                // save output
                val value1Index = codeSequence[currentIndex + 1]
                output = codeSequence[value1Index]
                currentIndex += 2
            }
            5 -> {
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
            6 -> {
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
            7 -> {
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
            8 -> {
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
            else -> {
                halt = true
                output = -1
                break@loop
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

fun <T> permute(input: List<T>): List<List<T>> {
    if (input.size == 1) return listOf(input)
    val perms = mutableListOf<List<T>>()
    val toInsert = input[0]
    for (perm in permute(input.drop(1))) {
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}