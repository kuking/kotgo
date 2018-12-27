package uk.kukino.kotgo

data class Cross(val x: Int, val y: Int) {
    companion object {
        fun fromString(coord: String): Cross {
            val coordt = coord.trim()
            assert(coordt.length == 2) { "This should have two letters" }
            assert(coordt[0].isLetter()) { "First character should be a letter but got ${coord[0]}" }
            assert(coordt[1].isDigit()) { "Second character should be a digit but got ${coord[1]}" }
            return Cross(coordt[0].toUpperCase().toInt() - 'A'.toInt(), coordt[1].toInt() - '1'.toInt())
        }
    }

    fun idx(size: Int) = y * size + x
}

