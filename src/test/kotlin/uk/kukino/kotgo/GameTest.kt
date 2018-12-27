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

    @Test
    fun play_simple() {
//        game.play()
    }

}