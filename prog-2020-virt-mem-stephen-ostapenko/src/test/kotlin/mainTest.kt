import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class mainTest {

    // Tests for checkInput ============================================================================================
    @Test
    fun `rubbish input`() {
        assertEquals(false, checkInput(listOf("2", "3", "kek")))
    }

    @Test
    fun `correct input`(){
        assertEquals(true, checkInput(listOf("4", "2", "3", "4", "1", "2", "1")))
    }

    @Test
    fun `another correct input`() {
        assertEquals(true, checkInput(listOf("7", "3", "1", "2", "4", "1", "4", "7", "6", "5", "4", "3")))
    }

    @Test
    fun `equal elements in initial memory`() {
        assertEquals(false, checkInput(listOf("4", "3", "1", "1", "2", "3", "2", "4")))
    }

    @Test
    fun `page number out of bounds`() {
        assertEquals(false, checkInput(listOf("5", "2", "1", "2", "6")))
    }

    @Test
    fun `wrong page number`() {
        assertEquals(false, checkInput(listOf("5", "2", "1", "2", "0")))
    }

    @Test
    fun `wrong initial memory`() {
        assertEquals(false, checkInput(listOf("5", "2", "1", "6", "3")))
    }

    @Test
    fun `wrong n`() {
        assertEquals(false, checkInput(listOf("-2", "4", "1", "2", "3", "4", "2")))
    }

    @Test
    fun `wrong m`() {
        assertEquals(false, checkInput(listOf("4", "-2", "1", "2", "3", "4", "2")))
    }

    @Test
    fun `n in less than m`() {
        assertEquals(false, checkInput(listOf("4", "5", "1", "2", "3", "4", "2")))
    }

    @Test
    fun `not enough elements in initial memory`() {
        assertEquals(false, checkInput(listOf("10", "8", "1", "2", "3", "4", "2")))
    }

    // Tests for processFIFO ===========================================================================================
    @Test
    fun `test processFIFO`() {
        assertEquals(5, processFIFO(7, 3, listOf(1, 2, 4), listOf(1, 4, 7, 6, 5, 4, 3)))
    }

    // Tests for processLRU ===========================================================================================
    @Test
    fun `test processLRU`() {
        assertEquals(5, processLRU(3, listOf(1, 2, 4), listOf(1, 4, 7, 6, 5, 4, 3)))
    }

    // Tests for processOPT ===========================================================================================
    @Test @ExperimentalStdlibApi
    fun `test processOPT`() {
        assertEquals(4, processOPT(7, listOf(1, 2, 4), listOf(1, 4, 7, 6, 5, 4, 3)))
    }
}