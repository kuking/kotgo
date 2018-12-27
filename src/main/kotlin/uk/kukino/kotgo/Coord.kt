package uk.kukino.kotgo

data class Coord(val x: Byte, val y: Byte) {
    companion object {
        fun fromString(coord: String): Coord {
            val coordt = coord.trim().toUpperCase()

            if (coordt == "PASS") return Coord(-1, -1) // special case, -1, -1 is pass

            assert(coordt.length == 2 || coordt.length == 3) { "This should have two or three letters" }
            assert(coordt[0].isLetter()) { "First character should be a letter but got ${coord[0]}" }
            assert(coordt[1].isDigit()) { "Second character should be a digit but got ${coord[1]}" }
            return Coord(
                    (coordt[0].toInt() - 'A'.toInt()).toByte(),
                    (coordt.substring(1).toInt() - 1).toByte()
            )
        }
    }

    fun isPass() = (x.toInt() == -1 && y.toInt() == -1)

    fun idx(size: Int) = y * size + x
}

