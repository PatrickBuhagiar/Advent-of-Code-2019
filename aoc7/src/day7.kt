import java.io.File

fun main() {
    val code = getCode()
    val perms = permute(listOf(5, 6, 7, 8, 9))

    val max = perms.map { perm ->

        val amps = mutableListOf<Amp>()
        for (i in perm.indices) {
            amps.add(Amp(code, arrayListOf(perm[i])))
        }
        var halt = false
        do {
            for (i in amps.indices) {
                val prevIndex = if (i != 0) i - 1 else perm.size - 1
                val res = amps[prevIndex].getOutput()
                if (amps[prevIndex].isHalted()) {
                    halt = true
                    break
                } else {
                    amps[i].addInput(res)
                    amps[i].start()
                }
            }
        } while (!halt)
        amps[4].getHighestOutput()
    }.max()
    print(max)
}

private fun getCode(): MutableList<Int> {
    val readText = File("src/input.csv").readText()
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