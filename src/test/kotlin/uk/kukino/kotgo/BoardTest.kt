package uk.kukino.kotgo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class BoardTest {

    private var board = Board(19)

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

}
