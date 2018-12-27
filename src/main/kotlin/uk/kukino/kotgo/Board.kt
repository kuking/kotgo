package uk.kukino.kotgo

class Board(var size: Int) {

    var intersections: Array<Color>

    init {
        if (size < 4) throw IllegalArgumentException("Board size should be at least 4 intersections wide")
        if (size >= 50) throw IllegalArgumentException("Board size too big")
        this.intersections = Array(size * size) { Color.EMPTY }
        //TODO: handicaps here
    }

    fun get(cross: Cross) = intersections[cross.idx(size)]

    fun set(cross: Cross, col: Color) {
        if (cross.x < 0 || cross.y < 0 || cross.x >= size || cross.y >= size) {
            throw IllegalArgumentException("Coordinates out of the board!")
        }
        intersections[cross.idx(size)] = col
    }

}