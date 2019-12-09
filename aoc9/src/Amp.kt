class Amp(code: MutableList<Long>, private val input: MutableList<Long>) {
    private val codeSequence: MutableList<Long> = code.toMutableList()
    private var halt: Boolean = false
    private var inputCounter = 0
    private var currentIndex = 0
    private var output = 0L
    private var highestOutput = 0L
    private var currentValue = 0L
    private var relativeBase = 0

    fun isHalted(): Boolean {
        return this.halt
    }

    fun getOutput(): Long {
        return output
    }

    fun getHighestOutput(): Long {
        return highestOutput
    }

    fun addInput(v: Long) {
        input.add(v)
    }

    fun start(): Boolean {
        currentValue = codeSequence[currentIndex]
        halt = false
        loop@ while (currentValue != 99L) {
            val parameters = parseParameterMode(currentValue)
            when (parameters[0]) {
                1 -> {
                    //add opp
                    val (resultIndex, v1, v2) = getValuesAndResultIndex(parameters)
                    codeSequence[resultIndex.toInt()] = v1 + v2
                    currentIndex += 4
                }
                2 -> {
                    // multiply
                    val (resultIndex, v1, v2) = getValuesAndResultIndex(parameters)
                    codeSequence[resultIndex.toInt()] = v1 * v2
                    currentIndex += 4
                }
                3 -> {
                    // save input
                    val value1Index = codeSequence[currentIndex + 1]
                    if (parameters[1] == 2) {
                        codeSequence[value1Index.toInt()  + relativeBase] = input[inputCounter]
                    } else {
                        codeSequence[value1Index.toInt()] = input[inputCounter]
                    }
                    inputCounter += 1
                    currentIndex += 2
                }
                4 -> {
                    // save output
                    val value1Index = codeSequence[currentIndex + 1]
                    output = getValue(parameters[1], value1Index)
                    highestOutput = if (highestOutput < output) output else highestOutput
                    currentIndex += 2
                    print("$output, ")
                }
                5 -> {
                    // jump if true
                    val (resultIndex, v1, v2) = getValuesAndResultIndex(parameters)
                    if (v1 != 0L) {
                        currentIndex = v2.toInt()
                    } else {
                        currentIndex += 3
                    }
                }
                6 -> {
                    // jump if false
                    val (resultIndex, v1, v2) = getValuesAndResultIndex(parameters)
                    if (v1 == 0L) {
                        currentIndex = v2.toInt()
                    } else {
                        currentIndex += 3
                    }
                }
                7 -> {
                    // less than
                    val (resultIndex, v1, v2) = getValuesAndResultIndex(parameters)
                    if (v1 < v2) {
                        codeSequence[resultIndex.toInt()] = 1L
                    } else {
                        codeSequence[resultIndex.toInt()] = 0L
                    }
                    currentIndex += 4
                }
                8 -> {
                    // greater than
                    val (resultIndex, v1, v2) = getValuesAndResultIndex(parameters)

                    if (v1 == v2) {
                        codeSequence[resultIndex.toInt()] = 1L
                    } else {
                        codeSequence[resultIndex.toInt()] = 0L
                    }
                    currentIndex += 4
                }
                9 -> {
                    val value1Index = codeSequence[currentIndex + 1]
                    val v1 = getValue(parameters[1], value1Index)
                    relativeBase += v1.toInt()
                    currentIndex += 2
                }
            }

            currentValue = codeSequence[currentIndex]
        }
        halt = true
        return false
    }

    private fun getValuesAndResultIndex(parameters: List<Int>): Triple<Long, Long, Long> {
        val value1Index = codeSequence[currentIndex + 1]
        val value2Index = codeSequence[currentIndex + 2]
        var resultIndex = if (currentIndex + 3 >= codeSequence.size) -1 else codeSequence[currentIndex + 3]
        val v1 = getValue(parameters[1], value1Index)
        val v2 = getValue(parameters[2], value2Index)
        if (parameters[3] == 2) {
            resultIndex += relativeBase
        }
        return Triple(resultIndex, v1, v2)
    }

    private fun getValue(parameter: Int, index: Long): Long {
        return when (parameter) {
            0 -> {
                codeSequence[index.toInt()]
            }
            2 -> {
                codeSequence[index.toInt() + relativeBase]
            }
            else -> {
                index
            }
        }
    }

    private fun parseParameterMode(currentValue: Long): List<Int> {
        val s = currentValue.toString()
        when (s.length) {
            1, 2 -> return listOf(currentValue.toInt(), 0, 0, 0)
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