package uk.kukino.kotgo

class Board(var size: Int) {

    private var intersections: Array<Color>
    var zorbrist: Long
        private set

    init {
        if (size < 4) throw IllegalArgumentException("Board size should be at least 4 intersections wide")
        if (size >= 50) throw IllegalArgumentException("Board size too big")
        this.intersections = Array(size * size) { Color.EMPTY }

        zorbrist = 0
        intersections.forEachIndexed { i, c -> zorbrist = zorbrist xor Zobrist.n(c, i) }
    }

    fun get(coord: Coord) = intersections[coord.idx(size)]
    fun get(crossSt: String) = get(Coord.fromString(crossSt))

    fun set(coord: Coord, col: Color) {
        if (coord.x < 0 || coord.y < 0 || coord.x >= size || coord.y >= size) {
            throw IllegalArgumentException("Coordinates out of the board!")
        }

        val idx = coord.idx(size)
        val curr = intersections[idx]
        zorbrist = zorbrist xor Zobrist.n(curr, idx) xor Zobrist.n(col, idx)
        intersections[idx] = col
    }

    fun copy(): Board {
        val new = Board(size)
        new.intersections = this.intersections.copyOf()
        new.zorbrist = this.zorbrist
        return new
    }

    private fun paintAt(coord: Coord, color: Color, painted: MutableSet<Coord>): Set<Coord> {
        coord.adjacents(size)
                .filter { get(it) == color }
                .filter { !painted.contains(it) }
                .forEach {
                    painted.add(it)
                    paintAt(it, color, painted)
                }
        return painted
    }

    private fun liberties(stones: Set<Coord>): Set<Coord> {
        return stones.flatMap { it.adjacents(size) }
                .toSet()
                .filter { get(it) == Color.EMPTY }
                .toSet()
    }

    fun chainAt(coord: Coord): Chain {
        val color = get(coord)
        if (color == Color.EMPTY) return Chain(setOf(), Color.EMPTY, setOf())
        val stones = paintAt(coord, color, mutableSetOf(coord))
        val liberties = liberties(stones)
        return Chain(stones.toSet(), color, liberties)
    }


}