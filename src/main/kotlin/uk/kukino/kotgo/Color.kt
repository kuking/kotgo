package uk.kukino.kotgo

enum class Color {
    BLACK, WHITE, EMPTY;

    fun opposite(): Color {
        return when {
            this == BLACK -> WHITE
            this == WHITE -> BLACK
            else -> EMPTY
        }
    }
}
