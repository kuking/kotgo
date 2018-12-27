package uk.kukino.kotgo

data class Game(val size: Int, val handicap: Int = 0) {

    open class InvalidPly(message: String) : Exception(message)
    class InvalidPlayer : InvalidPly("Invalid player")
    class InvalidMove : InvalidPly("Invalid move")

    var board: Board = Board(size)
        private set

    var nextPlayer: Color = Color.BLACK
        private set

    var finished: Boolean = false
        private set

    var moves: List<Move> = mutableListOf()


    init {

    }

    fun play(st: String) = play(Move.fromString(st))

    fun play(move: Move) {
        if (move.player != nextPlayer) throw InvalidPlayer()
        if (board.get(move.coord) != Color.EMPTY) throw InvalidMove()
        board.set(move.coord, move.player)
        nextPlayer = if (nextPlayer == Color.WHITE) Color.BLACK else Color.WHITE
    }

    fun capturedCount(): List<Int> {
        return listOf(0, 0)
    }

    fun capturedCount(color: Color): Int {
        return 0
    }


}