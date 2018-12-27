package uk.kukino.kotgo

import org.junit.Test
import kotlin.test.assertEquals

class CoordTest {

    @Test(expected = AssertionError::class)
    fun fromString_length_1() {
        Coord.fromString("")
    }

    @Test(expected = AssertionError::class)
    fun fromString_length_2() {
        Coord.fromString("1234234")
    }

    @Test(expected = AssertionError::class)
    fun fromString_nonChar() {
        Coord.fromString("11")
    }

    @Test
    fun fromString_happy() {
        val c = Coord.fromString("C2")
        assertEquals(2, c.x)
        assertEquals(1, c.y)
    }

    @Test
    fun fromString_big_happy() {
        val c = Coord.fromString("Q16")
        assertEquals(16, c.x)
        assertEquals(15, c.y)
    }

    @Test
    fun fromString_happy_lowercase() {
        val c = Coord.fromString("f5")
        assertEquals(5, c.x)
        assertEquals(4, c.y)
    }

    @Test
    fun fromString_happy_untrimmed() {
        val c = Coord.fromString("  f5  ")
        assertEquals(5, c.x)
        assertEquals(4, c.y)
    }

    @Test
    fun pass() {
        val c = Coord.fromString(" PaSs  ")
        assert(c.isPass())
    }

}