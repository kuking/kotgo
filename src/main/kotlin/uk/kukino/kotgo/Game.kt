package uk.kukino.kotgo

class Game(val size: Int, val handicap: Int = 0, val komi: Float = 5.5f) {

    abstract class InvalidMove(message: String) : Exception(message)
    class InvalidPlayer : InvalidMove("Invalid player")
    class GameFinished : InvalidMove("The game is already finished")
    class MoveIsSuicide : InvalidMove("Suicide")
    class MoveIsKO : InvalidMove("Invalid Move; it is KO")
    class MoveNotEmpty : InvalidMove("The position is not empty")

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
    private val superKo: MutableSet<Long> = mutableSetOf()

    init {
        superKo.add(board.zorbrist)
    }

    fun copy(): Game {
        val new = Game(size, handicap, komi)
        new.board = this.board.copy()
        new.nextPlayer = this.nextPlayer
        new.finished = this.finished
        new.whiteCaptured = this.whiteCaptured
        new.blackCaptured = this.blackCaptured
        new.moves.addAll(this.moves)
        new.superKo.addAll(this.superKo)
        return new
    }

    fun play(st: String) = play(Move.fromString(st))

    fun play(move: Move) {
        val player = this.nextPlayer
        val opponent = player.opposite()

        if (finished) throw GameFinished()
        if (move.player != player) throw InvalidPlayer()

        /*
        \Pass
         */
        if (move.isPass()) return applyMove(move, opponent)

        /*
        \Stone
         */
        if (board.get(move.coord) != Color.EMPTY) throw MoveNotEmpty()

        // basic scan for 'fast play'
        val adjacentChains = move.coord.adjacents(size).map { board.chainAt(it) }
        val oneLibertyChains = adjacentChains.filter { it.liberties.size == 1 }

        val maybeKilling = oneLibertyChains.filter { it.color == opponent }.count() > 0
        val maybeSuicide = move.coord.adjacents(size).filter { board.get(it) == Color.EMPTY }.count() == 0
        if (!maybeSuicide && !maybeKilling) return applyMove(move, opponent)

        // tries to play the move in a mock board to analyse the whole outcome
        return complexPlay(move, opponent, oneLibertyChains)
    }

    private fun complexPlay(move: Move, opponent: Color, oneLibertyChains: List<Chain>) {
        val mock = this.board.copy()

        val killCount = oneLibertyChains
                .filter { it.color == opponent }
                .map { capture(mock, it) }
                .fold(listOf(0, 0)) { a, b -> listOf(a[0] + b[0], a[1] + b[1]) }

        mock.set(move.coord, move.player)
        if (superKo.contains(mock.zorbrist)) throw MoveIsKO()
        val isSuicide = mock.chainAt(move.coord).liberties.isEmpty()
        if (isSuicide) throw MoveIsSuicide()

        this.board = mock
        this.blackCaptured += killCount[0]
        this.whiteCaptured += killCount[1]

        return applyMove(move, opponent)

    }

    private fun capture(b: Board, chain: Chain): List<Int> {
        var black = 0
        var white = 0
        chain.stones.forEach {
            when (b.get(it)) {
                Color.BLACK -> black++
                Color.WHITE -> white++
                Color.EMPTY -> {
                }
            }
            b.set(it, Color.EMPTY)
        }
        return listOf(black, white)
    }

    private fun applyMove(move: Move, opponent: Color) {
        nextPlayer = opponent
        moves.add(move)
        if (move.isPass()) {
            finished = moves.size >= 2 && moves[moves.size - 2].isPass()
        } else {
            board.set(move.coord, move.player)
            superKo.add(board.zorbrist)
        }
    }

    fun captured(): List<Int> {
        return listOf(blackCaptured, whiteCaptured)
    }

}