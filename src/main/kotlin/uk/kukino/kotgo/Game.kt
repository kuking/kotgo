package uk.kukino.kotgo

data class Game(val size: Int, val handicap: Int = 0, val komi: Float = 5.5f) {

    open class InvalidPly(message: String) : Exception(message)
    class InvalidPlayer : InvalidPly("Invalid player")
    open class InvalidMove(message: String = "Invalid Move") : InvalidPly(message)
    class InvalidMoveSuicide : InvalidMove("Suicide")

    var board: Board = Board(size)
        private set

    var nextPlayer: Color = Color.BLACK
        private set

    var finished: Boolean = false
        private set

    var whiteCaptured: Int = 0
        private set

    var blackCaptured: Int = 0
        private set

    private val moves: MutableList<Move> = mutableListOf()

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
            nextPlayer = nextPlayer.opposite()
            return
        }

        // stone
        if (board.get(move.coord) != Color.EMPTY) throw InvalidMove()

        // simple scan
        val player = nextPlayer
        val opponent = nextPlayer.opposite()

        val adjacentChains = move.coord.adjacents(size).map { board.chainAt(it) }
        val oneLibertyChains = adjacentChains.filter { it.liberties.size == 1 }
        val isSuicide = oneLibertyChains.filter { it.color == player }.count() > 0
        val isKilling = oneLibertyChains.filter { it.color == opponent }.count() > 0

        if (!isSuicide && !isKilling) { // simple move
            moves.add(move)
            board.set(move.coord, move.player)
            nextPlayer = nextPlayer.opposite()
            return
        }

        if (isSuicide && !isKilling) { // simple suicide
            throw InvalidMoveSuicide()
        }

        if (isKilling) { // ko-less implementation -- needs to be done in a temp Board
            oneLibertyChains
                    .filter { it.color == opponent }
                    .flatMap { it.stones }
                    .forEach {
                        if (opponent == Color.BLACK) blackCaptured++ else whiteCaptured++
                        board.set(it, Color.EMPTY)
                    }
            moves.add(move)
            board.set(move.coord, move.player)
            nextPlayer = opponent
            return
        }

        // complex, we need to get a copy of the board and play around


        moves.add(move)
        board.set(move.coord, move.player)
        nextPlayer = opponent
    }

    fun captured(): List<Int> {
        return listOf(blackCaptured, whiteCaptured)
    }

}