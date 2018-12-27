package uk.kukino.kotgo

data class Move(var player: Color, var coord: Coord) {

    init {
        assert(player != Color.EMPTY) { "Player color only be BLACK or WHITE, not EMPTY" }
    }

    fun isPass() = coord.isPass()

    companion object {
        fun fromString(str: String): Move {
            val tokens = str
                    .trim()
                    .toUpperCase()
                    .split(" ")
                    .filter { !it.isBlank() }
            assert(tokens.size == 2) { "Invalid format, invalid amount of tokens" }

            val colorSt = tokens.first()
            val color = if (colorSt == "B" || colorSt == "BLACK") Color.BLACK
            else if (colorSt == "W" || colorSt == "WHITE") Color.WHITE
            else throw AssertionError("Invalid Format for player")

            return Move(color, Coord.fromString(tokens.last()))
        }
    }


}

