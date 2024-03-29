class Amp(
    code: MutableList<Int>,
    private val input: MutableList<Int>
) {
    private val codeSequence: MutableList<Int> = code.toMutableList()
    private var halt: Boolean = false
    private var inputCounter = 0
    private var currentIndex = 0
    private var output = 0
    private var highestOutput = 0
    private var currentValue = 0

    fun isHalted(): Boolean {
        return this.halt
    }

    fun getOutput(): Int {
        return output
    }

    fun getHighestOutput(): Int {
        return highestOutput
    }

    fun addInput(v: Int) {
        input.add(v)
    }

    fun start(): Boolean {
        currentValue = codeSequence[currentIndex]
        halt = false
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
                    highestOutput = if (highestOutput < output) output else highestOutput
                    currentIndex += 2
                    return halt
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
            }

            currentValue = codeSequence[currentIndex]
        }
        halt = true
        return false
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
        return arrayListOf(0)
    }
}