package uk.kukino.kotgo

data class Chain(var stones: Set<Coord>, var color: Color, var liberties: Set<Coord>) {

    var size = stones.size

    fun isEmpty() = color == Color.EMPTY && stones.isEmpty() && liberties.isEmpty()

}

