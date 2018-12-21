package uk.kukino.kotgo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class BoardTest {

    @Test
    fun boardInit_sizeShouldBeAtLeastFour() {
        for (size in -5 until 3) {
            try {
                Board(size)
                fail("A board size $size should not be possible to create")
            } catch (e: IllegalArgumentException) {
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun boardInit_sizeShouldBeLessThan50() {
        Board(50)
    }

    @Test
    fun boardInit_validSizeAllOk() {
        for (size in 4 until 50) {
            val board = Board(size)
            assertEquals(size, board.size)
        }
    }

    @Test
    fun boardInit_startingBoardIsEmpty() {
        for (size in 4 until 25) {
            val board = Board(size)
            for (x in 0 until board.size) {
                for (y in 0 until board.size) {
                    assertEquals(Color.EMPTY, board.get(Cross(x, y)))
                }
            }
        }
    }

    @Test
    fun boardBasic_setOne() {
        val board = Board(19)
        val cell = Cross(5, 5)
        board.set(cell, Color.BLACK)
        assertEquals(Color.BLACK, board.get(cell))
    }


}
