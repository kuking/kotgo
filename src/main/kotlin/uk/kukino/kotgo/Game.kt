package uk.kukino.kotgo

data class Game(val size: Int, val handicap: Int = 0, val komi: Float = 5.5f) {

    open class InvalidPly(message: String) : Exception(message)
    class InvalidPlayer : InvalidPly("Invalid player")
    class InvalidMove : InvalidPly("Invalid move")

    var board: Board = Board(size)
        private set

    var nextPlayer: Color = Color.BLACK
        private set

    var finished: Boolean = false
        private set

    var moves: MutableList<Move> = mutableListOf()


    init {

    }

    fun play(st: String) = play(Move.fromString(st))

    fun play(move: Move) {
        if (finished) throw InvalidMove()
        if (move.player != nextPlayer) throw InvalidPlayer()

        // pass
        if (move.isPass()) {
            moves.add(move)
            if (moves.size >= 2 && moves[moves.size - 2].isPass()) {
                finished = true
            }
            nextPlayer = if (nextPlayer == Color.WHITE) Color.BLACK else Color.WHITE
            return
        }

        // stone
        if (board.get(move.coord) != Color.EMPTY) throw InvalidMove()

        // stone, simple optimisation all surrounding chains have more than one liberty
        


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