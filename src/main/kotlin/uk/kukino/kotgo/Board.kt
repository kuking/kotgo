package uk.kukino.kotgo

enum class Color {
    BLACK, WHITE, EMPTY
}

data class Cross(val x: Int, val y: Int) {
    fun idx(size: Int) = y * size + x
}

class Board(var size: Int, var handicap: Int = 0) {

    var intersections: Array<Color>

    init {
        if (size < 4) throw IllegalArgumentException("Board size should be at least 4 intersections wide")
        if (size >= 50) throw IllegalArgumentException("Board size too big")
        this.intersections = Array(size * size) { Color.EMPTY }
        //TODO: handicaps here
    }

    fun get(cross: Cross) = intersections[cross.idx(size)]

    fun set(cross: Cross, col: Color) {
        intersections[cross.idx(size)] = col
    }

}