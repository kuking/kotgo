package uk.kukino.kotgo

data class Board(var size: Int) {

    var intersections: Array<Color>

    init {
        if (size < 4) throw IllegalArgumentException("Board size should be at least 4 intersections wide")
        if (size >= 50) throw IllegalArgumentException("Board size too big")
        this.intersections = Array(size * size) { Color.EMPTY }
        //TODO: handicaps here
    }

    fun get(coord: Coord) = intersections[coord.idx(size)]
    fun get(crossSt : String) = get(Coord.fromString(crossSt))

    fun set(coord: Coord, col: Color) {
        if (coord.x < 0 || coord.y < 0 || coord.x >= size || coord.y >= size) {
            throw IllegalArgumentException("Coordinates out of the board!")
        }
        intersections[coord.idx(size)] = col
    }

}