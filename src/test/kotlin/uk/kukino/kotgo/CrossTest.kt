package uk.kukino.kotgo

import org.junit.Test
import kotlin.test.assertEquals

class CrossTest {

    @Test(expected = AssertionError::class)
    fun cross_fromString_length_1() {
        Cross.fromString("")
    }

    @Test(expected = AssertionError::class)
    fun cross_fromString_length_2() {
        Cross.fromString("1234234")
    }

    @Test(expected = AssertionError::class)
    fun cross_fromString_nonChar() {
        Cross.fromString("11")
    }

    @Test
    fun cross_fromString_happy() {
        val c = Cross.fromString("C2")
        assertEquals(2, c.x)
        assertEquals(1, c.y)
    }

    @Test
    fun cross_fromString_happy_lowercase() {
        val c = Cross.fromString("f5")
        assertEquals(5, c.x)
        assertEquals(4, c.y)
    }

    @Test
    fun cross_fromString_happy_untrimmed() {
        val c = Cross.fromString("  f5  ")
        assertEquals(5, c.x)
        assertEquals(4, c.y)
    }

}