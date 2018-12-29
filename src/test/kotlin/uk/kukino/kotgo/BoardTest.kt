package uk.kukino.kotgo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class BoardTest {

    private var board = Board(19)
    private var K10 = Coord.fromString("K10")

    @Test
    fun init_sizeShouldBeAtLeastFour() {
        for (size in -5 until 3) {
            try {
                Board(size)
                fail("A board size $size should not be possible to create")
            } catch (e: IllegalArgumentException) {
                // happy path
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun init_sizeShouldBeLessThan50() {
        Board(50)
    }

    @Test
    fun init_validSizedBoards() {
        for (size in 4 until 50) {
            val board = Board(size)
            assertEquals(size, board.size)
        }
    }

    @Test
    fun init_startingBoardIsEmpty() {
        for (size in 14 until 25) {
            val board = Board(size)
            for (x in 0 until board.size) {
                for (y in 0 until board.size) {
                    assertEquals(Color.EMPTY, board.get(Coord(x.toByte(), y.toByte())))
                }
            }
        }
    }

    @Test
    fun basic_setOne() {
        val cell = Coord(5, 5)
        board.set(cell, Color.BLACK)
        assertEquals(Color.BLACK, board.get(cell))
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_N() {
        board.set(Coord(5, -1), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_NE() {
        board.set(Coord(19, -1), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_E() {
        board.set(Coord(19, 5), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_SE() {
        board.set(Coord(19, 19), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_S() {
        board.set(Coord(5, 19), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_SW() {
        board.set(Coord(-1, 19), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_W() {
        board.set(Coord(-1, 5), Color.BLACK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun basic_setOutsideLimits_NW() {
        board.set(Coord(-1, -1), Color.BLACK)
    }

    @Test
    fun boardIsPlaceholder_noGameRulesEnforced() {
        val cell = Coord(5, 5)
        board.set(cell, Color.BLACK)
        assertEquals(Color.BLACK, board.get(cell))
        board.set(cell, Color.WHITE)
        assertEquals(Color.WHITE, board.get(cell))
        board.set(cell, Color.EMPTY)
        assertEquals(Color.EMPTY, board.get(cell))
    }

    @Test
    fun chainAt_empty() {
        assert(board.chainAt(Coord.fromString("K10")).isEmpty())
    }

    @Test
    fun chainAt_simplest() {
        board.set(K10, Color.BLACK)

        val chain = board.chainAt(K10)

        assert(chain.size == 1)
        assert(chain.stones.contains(K10))
        assert(chain.liberties == listOf("k9", "k11", "j10", "l10").map { Coord.fromString(it) }.toSet())
    }

    @Test
    fun chainAt_complex() {
        // see GameTest.play_kill_four_kills for board
        listOf("k10", "k9", "j10", "j9", "d10", "d9", "d8", "d11")
                .map { Coord.fromString(it) }
                .forEach { board.set(it, Color.BLACK) }
        listOf("L10", "L9", "k11", "j11", "h10", "h9", "j8", "k8")
                .map { Coord.fromString(it) }
                .forEach { board.set(it, Color.WHITE) }

        val chain = board.chainAt(K10)
        assert(chain.size == 4)
        assert(chain.stones.containsAll(listOf("k10", "k9", "j10", "j9").map { Coord.fromString(it) }))
        assert(chain.liberties.isEmpty())

        val chain2 = board.chainAt(Coord.fromString("D11"))
        assert(chain2.size == 4)
        assert(chain2.stones.containsAll(listOf("d10", "d9", "d8", "d11").map { Coord.fromString(it) }))
        assert(chain2.liberties.size == 10)

        val chain3 = board.chainAt(Coord.fromString("H10"))
        assert(chain3.size == 2)
        assert(chain3.stones.containsAll(listOf("h10", "h9").map { Coord.fromString(it) }))
        assert(chain3.liberties.size == 4)
    }

    @Test
    fun copy_is_deep() {
        val c = Coord.fromString("A1")
        board.set(c, Color.BLACK)
        assert(board.copy().get(c) == board.get(c))
    }

    @Test
    fun zobrist_works() {
        val c = Coord(5, 5)
        val z1 = board.zorbrist
        board.set(c, Color.EMPTY)
        assert(z1 == board.zorbrist) { "Not modifying should be have same Zobrist value" }

        board.set(c, Color.WHITE)
        val z2w = board.zorbrist
        assert(z1 != z2w) { "Modifying should be different!" }

        board.set(c, Color.BLACK)
        val z3b = board.zorbrist
        assert(z2w != z3b)
        assert(z3b != z1)

        // returning to white should hold same value
        board.set(c, Color.WHITE)
        val z4w = board.zorbrist
        assert(z4w == z2w)

        // returning to empty should leave the same board again
        board.set(c, Color.EMPTY)
        assert(board.zorbrist == z1)
    }

}
