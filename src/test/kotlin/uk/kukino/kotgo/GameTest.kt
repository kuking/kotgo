package uk.kukino.kotgo

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {

    var game = Game(19)

    @Test
    fun init_firstPlayerBlack() {
        assertEquals(Color.BLACK, game.nextPlayer)
    }

    @Test
    @Ignore //TODO: Implement handicap tables, etc.
    fun init_firstPlayerWhiteWhenHandicap() {
        game = Game(19, 2)
        assertEquals(Color.WHITE, game.nextPlayer)
    }

    @Test(expected = Game.InvalidPlayer::class)
    fun play_invalid_color() {
        game.play(Move.fromString("white D4"))
    }

    @Test(expected = Game.InvalidPlayer::class)
    fun play_invalid_color_2() {
        game.play(Move.fromString("black D4"))
        game.play(Move.fromString("black Q16"))
    }

    @Test
    fun play_simplest() {
        game.play(Move.fromString("black D4"))
        assert(game.nextPlayer == Color.WHITE)
        assert(game.board.get(Cross.fromString("D4")) == Color.BLACK)

        game.play(Move.fromString("white Q16"))
        assert(game.nextPlayer == Color.BLACK)
        assert(game.board.get(Cross.fromString("Q16")) == Color.WHITE)
    }

    @Test(expected = Game.InvalidMove::class)
    fun play_playing_on_already_occupied() {
        game.play(Move.fromString("black D4"))
        game.play(Move.fromString("white D4"))
    }

    @Test
    fun play_simplest_kill() {
        game.play(Move.fromString("B B1"))
        game.play(Move.fromString("W A1"))
        game.play(Move.fromString("B A2"))

        assert(game.board.get(Cross.fromString("A1")) == Color.EMPTY)
        assert(game.capturedCount(Color.WHITE) == 1)

    }
}