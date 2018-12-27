package uk.kukino.kotgo

import org.junit.Test
import kotlin.test.assertEquals

class MoveTest {

    @Test(expected = AssertionError::class)
    fun emptyColorIsInvalid() {
        Move(Color.EMPTY, Coord.fromString("A1"))
    }

    @Test(expected = AssertionError::class)
    fun fromString_length_1() {
        Move.fromString("")
    }

    @Test(expected = AssertionError::class)
    fun fromString_length_2() {
        Move.fromString("1234234")
    }

    @Test(expected = AssertionError::class)
    fun fromString_incomplete() {
        Move.fromString("black")
    }

    @Test(expected = AssertionError::class)
    fun fromString_incomplete_2() {
        Move.fromString("  black")
    }

    @Test(expected = AssertionError::class)
    fun fromString_incomplete_3() {
        Move.fromString("  black c  ")
    }

    @Test
    fun fromString_happy() {
        val m = Move.fromString("BLACK C2")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test
    fun fromString_happy_short_player() {
        val m = Move.fromString("B C2")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test
    fun fromString_happy_lowercase() {
        val m = Move.fromString("black c2")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test
    fun fromString_happy_lowercase_short_player() {
        val m = Move.fromString("b c2")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test
    fun fromString_happy_hacker_case() {
        val m = Move.fromString("BlaCk c2")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test
    fun fromString_happy_untrimmed() {
        val m = Move.fromString("  black   c2  ")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test
    fun fromString_happy_short_untrimmed() {
        val m = Move.fromString("  b   c2  ")
        assertEquals(Color.BLACK, m.player)
        assertEquals(Coord.fromString("C2"), m.coord)
    }

    @Test(expected = AssertionError::class)
    fun fromString_invalid_hacky() {
        Move.fromString(" black watwat c2 ")
    }

    @Test
    fun fromString_pass() {
        val m = Move.fromString("white pass")
        assert(Color.WHITE == m.player)
        assert(m.isPass())
    }

}