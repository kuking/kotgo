package uk.kukino.kotgo

import kotlin.test.Test
import kotlin.test.fail
import kotlin.test.assertEquals

class BoardTest {

    @Test
    fun boardInit_sizeShouldBeAtLeastFour() {
        for (size in -5 until 3) {
            try {
                Board(size)
                fail("A board size $size should not be possible to create")
            } catch (e :IllegalArgumentException) {
                // good
            }
        }
    }

    @Test
    fun boardInit_validSizeAllOk() {
        for (size in 4 until 25) {
            val board = Board(size)
            assertEquals(size, board.size)
        }
    }
}
