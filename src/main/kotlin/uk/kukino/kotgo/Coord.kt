package uk.kukino.kotgo

data class Coord(val x: Byte, val y: Byte) {
    companion object {
        fun fromString(coord: String): Coord {
            val coordt = coord.trim().toUpperCase()

            if (coordt == "PASS") return Coord(-1, -1) // special case, -1, -1 is pass

            assert(coordt.length == 2 || coordt.length == 3) { "This should have two or three letters" }
            assert(coordt[0].isLetter()) { "First character should be a letter but got ${coord[0]}" }
            assert(coordt[1].isDigit()) { "Second character should be a digit but got ${coord[1]}" }
            assert(coordt[0] != 'I') { "'I' is not used as coordinate as it can be confusing" }

            val deltaI = if (coordt[0].toInt() > 'I'.toInt()) 1 else 0
            return Coord(
                    (coordt[0].toInt() - 'A'.toInt() - deltaI).toByte(),
                    (coordt.substring(1).toInt() - 1).toByte()
            )
        }
    }

    fun isPass() = (x.toInt() == -1 && y.toInt() == -1)

    fun idx(size: Int) = y * size + x

    fun adjacents(size: Int): List<Coord> {
        if (isPass()) return listOf()

        val res = mutableListOf<Coord>()
        if (x > 0) res.add(Coord((x - 1).toByte(), y))
        if (y > 0) res.add(Coord(x, (y - 1).toByte()))
        if (x + 1 < size) res.add(Coord((x + 1).toByte(), y))
        if (y + 1 < size) res.add(Coord(x, (y + 1).toByte()))

        return res.toList()
    }

    override fun toString(): String {
        val deltaI = if (x > 7) 1 else 0
        return if (isPass()) "PASS" else "${(x + 65 + deltaI).toChar()}${y + 1}"
    }
}

