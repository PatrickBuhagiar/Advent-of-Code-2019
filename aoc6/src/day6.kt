import java.io.File

fun main() {
    val orbits = readLines("src/input.txt")
    val orbitTree = OrbitTree("COM")

    orbits.forEach {
        val split = it.split(")")
        orbitTree.addChild(split[1], split[0])
    }

    println(orbitTree.getTotalDepth())

    val first = orbitTree.children
        .filter { it.hasChild("SAN") && it.hasChild("YOU") }
        .map { it.depthTo("SAN") + it.depthTo("YOU") }
        .min()

    print(first)


}

fun readLines(fileName: String) = File(fileName).readLines()

