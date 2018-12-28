package uk.kukino.kotgo

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class GameTest {

    var game = Game(19)

    @Test
    fun init_first_player_is_black() {
        assertEquals(Color.BLACK, game.nextPlayer)
    }

    @Test
    fun init_not_finished_at_start() {
        assert(!game.finished)
    }

    @Test
    @Ignore //TODO: Implement handicap tables, etc.
    fun init_firstPlayerWhiteWhenHandicap() {
        game = Game(19, 2)
        assertEquals(Color.WHITE, game.nextPlayer)
    }

    @Test
    fun ending_two_passes_is_finish() {
        game.play("black pass")
        game.play("white pass")
        assert(game.finished)
    }

    @Test(expected = Game.InvalidMove::class)
    fun ending_after_finished_any_move_is_invalid_move() {
        game.play("black pass")
        game.play("white pass")
        game.play("black pass")
    }

    @Test(expected = Game.InvalidPlayer::class)
    fun play_invalid_color() {
        game.play("white D4")
    }

    @Test(expected = Game.InvalidPlayer::class)
    fun play_invalid_color_2() {
        game.play("black D4")
        game.play("black Q16")
    }

    @Test
    fun play_simplest() {
        game.play("black D4")
        assert(game.nextPlayer == Color.WHITE)
        assert(game.board.get("D4") == Color.BLACK)

        game.play("white Q16")
        assert(game.nextPlayer == Color.BLACK)
        assert(game.board.get("Q16") == Color.WHITE)
    }

    @Test(expected = Game.InvalidMove::class)
    fun play_playing_on_already_occupied() {
        game.play("black D4")
        game.play("white D4")
    }

    @Test
    fun play_suicide_not_allowed() {
        /*
        3 . . . . . . . . . . . . . . . . . . .  3
        2 X X . . . . . . . O . . . . . . . . .  2
        1 O * X . . . . . . . . . . . . . . . .  1
          A B C D E F G H J K L M N O P Q R S T
         */
        listOf("B A2", "W A1", "B B2", "W K2", "B C1")
                .map { Move.fromString(it) }
                .forEach { game.play(it) }

        try {
            game.play(Move.fromString("W B1"))
            fail("This is suicide, should throw")
        } catch (e: Game.InvalidMoveSuicide) {
        }
    }

    @Test
    fun play_kill_simplest() {
        game.play("B B1")
        game.play("W A1")
        game.play("B A2")

        assert(game.board.get("A1") == Color.EMPTY)
        assert(game.captured() == listOf(0, 1))
    }

    @Test
    fun play_kill_four_kills() {
        /*
          A B C D E F G H J K L M N O P Q R S T
       19 . . . . . . . . . . . . . . . . . . . 19
       18 . . . . . . . . . . . . . . . . . . . 18
       17 . . . . . . . . . . . . . . . . . . . 17
       16 . . . + . . . . . + . . . . . + . . . 16
       15 . . . . . . . . . . . . . . . . . . . 15
       14 . . . . . . . . . . . . . . . . . . . 14
       13 . . . . . . . . . . . . . . . . . . . 13
       12 . . . . . . . . . . . . . . . . . . . 12
       11 . . . X . . . . O O . . . . . . . . . 11  X = black
       10 . . . X . . . O * * O . . . . + . . . 10  * = dead black (4)
        9 . . . X . . . O * * O . . . . . . . .  9  O = white
        8 . . . X . . . . O O . . . . . . . . .  8
        7 . . . . . . . . . . . . . . . . . . .  7
        6 . . . . . . . . . . . . . . . . . . .  6
        5 . . . . . . . . . . . . . . . . . . .  5
        4 . . . + . . . . . + . . . . . + . . .  4
        3 . . . . . . . . . . . . . . . . . . .  3
        2 . . . . . . . . . . . . . . . . . . .  2
        1 . . . . . . . . . . . . . . . . . . .  1
          A B C D E F G H J K L M N O P Q R S T
       */

        listOf("black k10", "white l10", "black k9", "white l9", "black j10", "white k11", "black j9", "white j11",
                "black d10", "white h10", "black d9", "white h9", "black d8", "white j8", "black d11", "white k8")
                .map { Move.fromString(it) }
                .forEach { game.play(it) }

        assert(game.board.get("k10") == Color.EMPTY)
        assert(game.board.get("k9") == Color.EMPTY)
        assert(game.board.get("j10") == Color.EMPTY)
        assert(game.board.get("j9") == Color.EMPTY)

        assert(game.captured() == listOf(4, 0))
    }

    @Test
    fun play_kill_then_double_kill() {
        listOf("black k10", "white l10", "black k9", "white l9", "black j10",
                "white k11", "black j9", "white j11", "black d10", "white h10", "black d9",
                "white h9", "black d8", "white j8", "black d11", "white k8", // up to here eats 4
                "black k10", "white j10", "black j9", "white k9") // then eats 2 different groups
                .map { Move.fromString(it) }
                .forEach { game.play(it) }

        assert(game.board.get("k10") == Color.EMPTY)
        assert(game.board.get("j9") == Color.EMPTY)
        assert(game.board.get("j10") == Color.WHITE)
        assert(game.board.get("k9") == Color.WHITE)

        assert(game.captured() == listOf(6, 0))
    }

    @Test
    fun play_ko_simple() {
        /*
       *  5 . . . . . . . . .
       *  4 . . . + . . . . .
       *  3 X X . . . . . . .
       *  2 . X . . . . . . .
       *  1(X)O O O . . . . .
       *    A B C D E F G H J
       */

        listOf("black a1", "white b1", "black a3", "white c1", "black b2", "white a2") // eat, valid
                .map { Move.fromString(it) }
                .forEach { game.play(it) }
        assert(game.captured() == listOf(1, 0))

        try {
            game.play("black a1")
            fail("It should have been an invalid move")
        } catch (e: Game.InvalidMove) {
        } // expected

        game.play("black b3")

        // two variants
        var game2 = game.copy()

        // variant: white finishes the ko
        game2.play("white a1")
        assert(game.captured() == listOf(1, 0))

        // variant: white plays somewhere else, black can play in the eye (ex ko) again
        game.play("white d1")
        game.play("black a1")
        try {
            game.play("white a2")
            fail("It should have been an invalid move")
        } catch (e: Game.InvalidMove) {
        } // expected, white can't eat at A2 because it is a KO
        assert(game.captured() == listOf(1, 1))
    }

    @Test
    @Ignore
    fun play_ko_super() {
        //TODO
    }
}