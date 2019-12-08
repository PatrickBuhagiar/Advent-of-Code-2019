class Layer(val pixels: List<Int>, val height: Int, val width: Int) {

    fun countZeros(): Int {
        return pixels.filter { it == 0 }.count()
    }

    fun calculate(): Int {
        val n1s = pixels
            .filter { it ==1 }
            .count()

        val n2s = pixels.filter { it == 2 }
            .count()
        
        return n1s * n2s
    }

    
}