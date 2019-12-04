fun main() {
    var combinations = 0

    for (code in 246540..787419) {
        val codeArray = toDigitArray(code)
        if (exactlyTwoAdjacentEqual(codeArray) && alwaysIncreasing(codeArray)) {
            combinations += 1
        }
    }


    println("combinations: $combinations")
}

fun toDigitArray(code: Int): List<Int> {
    val num1 = (code / 100000) % 10;
    val num2 = (code / 10000) % 10;
    val num3 = (code / 1000) % 10;
    val num4 = (code / 100) % 10;
    val num5 = (code / 10) % 10;
    val num6 = code % 10;

    return listOf(num1, num2, num3, num4, num5, num6)
}

fun twoAdjacentEqual(code: List<Int>): Boolean {
    var previous = 0
    for (i in 0..5) {
        if (i != 0 && code[i] == previous) {
            return true
        }
        previous = code[i]
    }
    return false
}

fun exactlyTwoAdjacentEqual(code: List<Int>): Boolean {
    var valid = false
    var previous = code[1]
    var previous2 = code[0]
    for (i in 2..5) {
        if (code[1] == code[0] && code[2] != code[1]) {
            return true
        } else if (previous == previous2 && code[i] != previous) {
            if (i > 2 && code[i-3] != previous2) {
                valid = true
            }
        } else if (i == 5 && code[i] == previous && code[i] != previous2 && !valid) {
            valid = true
        }
        previous = code[i]
        previous2 = code[i - 1]
    }
    return valid
}

fun alwaysIncreasing(code: List<Int>): Boolean {
    var previous = code[0]
    for (i in 1..5) {
        if (code[i] < previous) {
            return false
        }
        previous = code[i]
    }
    return true
}