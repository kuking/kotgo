package uk.kukino.kotgo

class Game(size: Int, handicap: Int = 0) {

    open class InvalidPly(message: String) : Exception(message)
    class InvalidPlayer : InvalidPly("Invalid player")
    class InvalidMove : InvalidPly("Invalid move")

    var board = Board(size)
    var nextPlayer: Color = Color.BLACK
    var moves: List<Move> = mutableListOf()

    init {

    }

    fun play(move: Move) {
        if (move.player != nextPlayer) throw InvalidPlayer()
        if (board.get(move.cross) != Color.EMPTY) throw InvalidMove()
        board.set(move.cross, move.player)
        nextPlayer = if (nextPlayer == Color.WHITE) Color.BLACK else Color.WHITE
    }


}