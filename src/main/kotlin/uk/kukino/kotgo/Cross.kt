package uk.kukino.kotgo

data class Cross(val x: Int, val y: Int) {
    companion object {
        fun fromString(coord: String): Cross {
            assert(coord.length == 2) { "This should have two letters" }
            assert(coord[0].isLetter()) { "First character should be a letter but got ${coord[0]}" }
            assert(coord[1].isDigit()) { "Second character should be a digit but got ${coord[1]}" }
            return Cross(coord[0].toUpperCase().toInt() - 'A'.toInt(), coord[1].toInt() - '1'.toInt())
        }
    }

    fun idx(size: Int) = y * size + x
}

