package uk.kukino.kotgo

enum class Color {
    BLACK, WHITE, EMPTY
}

data class Cell(val x: Int, val y: Int) {
    fun idx(size: Int) = y * size + x
}

class Board {

    var size: Int
    var handicap: Int
    var cells: Array<Color>

    constructor(size: Int, handicap: Int = 0) {
        if (size < 4) throw IllegalArgumentException("Board size should be at least 4 cells wide")
        this.size = size
        this.cells = Array(size * size) { Color.EMPTY }
        this.handicap = handicap
    }


    fun get(cell: Cell) = cells[cell.idx(size)]

    fun set(cell: Cell, col: Color) {
        cells[cell.idx(size)] = col
    }

}