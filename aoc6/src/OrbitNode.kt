import java.util.*

class OrbitNode(val name: String) {
    var orbits: OrbitNode? = null
    var orbitedBy: MutableList<OrbitNode>? = null

    fun orbits(node: OrbitNode) {
        orbits = node
    }

    fun orbitedBy(node: OrbitNode) {
        if (orbitedBy === null) {
            orbitedBy = mutableListOf(node)
        } else {
            orbitedBy!!.add(node)
        }
    }

    fun calculateDepth(): Int {
        return if (name == "COM") 0 else 1 + (orbits?.calculateDepth() ?: 0)
    }

    fun hasChild(child: String): Boolean {
        val firstOrNull = if (orbitedBy === null) null else orbitedBy!!.firstOrNull() { it.name == child }
        return if (firstOrNull != null) {
            true
        } else {
            val map = orbitedBy?.map { it.hasChild(child) }
            if (map === null) false else map.contains(true)
        }
    }

    fun depthTo(node: String): Int {
        return if (orbitedBy !== null) {
            orbitedBy!!.map { it.find(node, 0) }.max() !!
        } else 0
    }

    fun find(node: String, value: Int): Int {
        if (orbitedBy != null) {
            return if (orbitedBy!!.any { it.name == node }) {
                1 + value
            } else {
                val map = orbitedBy!!.map { it.find(node, value + 1) }
                val filter = map.filter { it != 0 }
                return if (filter.isEmpty()) 0 else filter.min()!!
            }
        }
        return 0
    }

}