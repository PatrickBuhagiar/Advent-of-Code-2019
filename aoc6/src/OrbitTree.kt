class OrbitTree(root: String) {
    var children = mutableListOf<OrbitNode>()
    var root = OrbitNode(root)

    fun addChild(child: String, parent: String) {
        val existingChildNode: OrbitNode? = children.firstOrNull { it.name == child }
        val existingParentNode = if (parent == "COM") root else children.firstOrNull { it.name == parent}
        
        if (existingChildNode === null) {
            val childNode = OrbitNode(child)
            children.add(childNode)
            if (existingParentNode === null) {
                val parentNode = OrbitNode(parent)
                parentNode.orbitedBy(childNode)
                childNode.orbits(parentNode)
                children.add(parentNode)
            } else {
                if (existingParentNode.name === root.name) {
                    root.orbitedBy(childNode)
                } else {
                    existingParentNode.orbitedBy(childNode)
                }
                childNode.orbits(existingParentNode)
            }
        } else {
            if (existingParentNode === null) {
                val parentNode = OrbitNode(parent)
                parentNode.orbitedBy(existingChildNode)
                existingChildNode.orbits(parentNode)
                children.add(parentNode)
            } else {
                if (existingParentNode.name === root.name) {
                    root.orbitedBy(existingChildNode)
                } else {
                    existingParentNode.orbitedBy(existingChildNode)
                }
                existingChildNode.orbits(existingParentNode)
            }
        }
    }
    
    fun getTotalDepth(): Int {
        return children.map { it.calculateDepth() }.sum()
    }
}