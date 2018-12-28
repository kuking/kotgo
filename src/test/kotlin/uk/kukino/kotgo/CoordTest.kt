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
        assertEquals(15, c.x)
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

    @Test(expected = AssertionError::class)
    fun fromString_I_do_not_exist() {
        Coord.fromString("I1")
    }

    @Test
    fun fromString_J_is_8_not_9_because_skipping_I() {
        assert(Coord.fromString("J1").x.toInt() == 8)
    }

    @Test
    fun pass() {
        val c = Coord.fromString(" PaSs  ")
        assert(c.isPass())
    }

    @Test
    fun adjacents_pass() {
        val c = Coord.fromString("pass")
        assert(c.adjacents(19).isEmpty())
    }

    @Test
    fun adjacents_1x1() {
        val c = Coord.fromString("A1")
        assert(c.adjacents(1).isEmpty())
    }

    @Test
    fun adjacents_19x19_simple() {
        val adjs = Coord.fromString("K10").adjacents(19)
        assert(adjs.size == 4)
        assert(adjs.containsAll(
                listOf("K9", "K11", "J10", "L10")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_N() {
        val adjs = Coord.fromString("K19").adjacents(19)
        assert(adjs.size == 3)
        assert(adjs.containsAll(
                listOf("K18", "J19", "L19")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_NE() {
        val adjs = Coord.fromString("T19").adjacents(19)
        assert(adjs.size == 2)
        assert(adjs.containsAll(
                listOf("T18", "S19")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_E() {
        val adjs = Coord.fromString("T10").adjacents(19)
        assert(adjs.size == 3)
        assert(adjs.containsAll(
                listOf("T11", "T9", "S10")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_SE() {
        val adjs = Coord.fromString("T1").adjacents(19)
        assert(adjs.size == 2)
        assert(adjs.containsAll(
                listOf("T2", "S1")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_S() {
        val adjs = Coord.fromString("K1").adjacents(19)
        assert(adjs.size == 3)
        assert(adjs.containsAll(
                listOf("K2", "J1", "L1")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_SW() {
        val adjs = Coord.fromString("A1").adjacents(19)
        assert(adjs.size == 2)
        assert(adjs.containsAll(
                listOf("A2", "B1")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun adjacents_19x19_W() {
        val adjs = Coord.fromString("A10").adjacents(19)
        assert(adjs.size == 3)
        assert(adjs.containsAll(
                listOf("A9", "A11", "B10")
                        .map { Coord.fromString(it) }))
    }


    @Test
    fun adjacents_19x19_NW() {
        val adjs = Coord.fromString("A19").adjacents(19)
        assert(adjs.size == 2)
        assert(adjs.containsAll(
                listOf("A18", "B19")
                        .map { Coord.fromString(it) }))
    }

    @Test
    fun toString_readable() {
        assert(Coord.fromString("a19").toString() == "A19")
        assert(Coord.fromString("A1").toString() == "A1")
        assert(Coord.fromString("J20").toString() == "J20")
        assert(Coord.fromString("K20").toString() == "K20")
        assert(Coord.fromString("PasS").toString() == "PASS")
    }

}