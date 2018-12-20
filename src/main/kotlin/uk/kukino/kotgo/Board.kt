package uk.kukino.kotgo

import java.lang.IllegalArgumentException

class Board {

    var size : Int

    constructor(size :Int) {
        if (size<4) throw IllegalArgumentException("Board size should be at least 4 cells wide")
        this.size = size
    }

    enum class Color {
        BLACK, WHITE, EMPTY
    }

    fun getColor(X : Int, Y : Int) : Color {
        return Color.EMPTY
    }

}