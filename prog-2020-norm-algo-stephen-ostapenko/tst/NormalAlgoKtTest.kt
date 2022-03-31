import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class NormalAlgoKtTest {

    // Test applySubstitution =====================================================================================
    @Test
    fun `classic substitution`() {
        assertEquals(applySubstitution("abacaba", "aba", "bbc"), "bbccaba")
    }

    @Test
    fun `full substitution`() {
        assertEquals(applySubstitution("lol", "lol", "kek"), "kek")
    }

    @Test
    fun `insertion test`() {
        assertEquals(applySubstitution("abc", "", "ab"), "ababc")
    }

    @Test
    fun `deletion test`() {
        assertEquals(applySubstitution("abcde", "bc", ""), "ade")
    }

    @Test
    fun `empty substitution`() {
        assertEquals(applySubstitution("abc", "", ""), "abc")
    }

    // Test applyScheme ===========================================================================================
    @Test
    fun `simple scheme`() {
        assertEquals(applyScheme("abc", arrayOf(Substitution("a", "bb", false))), "bbbc")
    }

    @Test
    fun `double scheme`() {
        assertEquals(applyScheme("abc", arrayOf(Substitution("a", "bb", false), Substitution("b", "d", false))), "dddc")
    }

    @Test
    fun `terminal scheme`() {
        assertEquals(applyScheme("aaaaaaaaaa", arrayOf(Substitution("a", "b", true))), "baaaaaaaaa")
    }

    @Test
    fun `no sub allowed`() {
        assertEquals(applyScheme("abcd", arrayOf(Substitution("xy", "xz", false))), "abcd")
    }

    @Test
    fun `cross subs`() {
        assertEquals(applyScheme("baaaa", arrayOf(Substitution("ba", "ac", false), Substitution("c", "b", false))), "aaaab")
    }

    // Test split ==================================================================================================
    @Test
    fun `no splitting needed`() {
        val a = split(arrayOf("1", "2", "3"))
        val b = arrayOf(arrayOf("1", "2", "3"))
        assertEquals(a.size, b.size)
        for (i in a.indices) {
            assertEquals(a[i].toList(), b[i].toList())
        }
    }

    @Test
    fun `single split`() {
        val a = split(arrayOf("1", QUERY_DELIMITER, "2"))
        val b = arrayOf(arrayOf("1"), arrayOf("2"))
        assertEquals(a.size, b.size)
        for (i in a.indices) {
            assertEquals(a[i].toList(), b[i].toList())
        }
    }

    @Test
    fun `multiple splits`() {
        val a = split(arrayOf("1", QUERY_DELIMITER, "2", QUERY_DELIMITER, "3"))
        val b = arrayOf(arrayOf("1"), arrayOf("2"), arrayOf("3"))
        assertEquals(a.size, b.size)
        for (i in a.indices) {
            assertEquals(a[i].toList(), b[i].toList())
        }
    }

    @Test
    fun `empty array in result`() {
        val a = split(arrayOf("1", QUERY_DELIMITER, QUERY_DELIMITER, "2"))
        val b = arrayOf(arrayOf("1"), arrayOf(), arrayOf("2"))
        assertEquals(a.size, b.size)
        for (i in a.indices) {
            assertEquals(a[i].toList(), b[i].toList())
        }
    }

    // Test checkSubstitution ======================================================================================
    @Test
    fun `simple sub`() {
        assertEquals(checkSubstitution("abc#kek"), true)
    }

    @Test
    fun `empty sub`() {
        assertEquals(checkSubstitution(""), false)
    }

    @Test
    fun `terminal sub`() {
        assertEquals(checkSubstitution("ab#xy."), true)
    }

    @Test
    fun `insertion sub`() {
        assertEquals(checkSubstitution("#t"), true)
    }

    @Test
    fun `deletion sub`() {
        assertEquals(checkSubstitution("q#"), true)
    }

    @Test
    fun `do nothing`() {
        assertEquals(checkSubstitution("#"), true)
    }

    @Test
    fun `wrong sub`() {
        assertEquals(checkSubstitution("abcd"), false)
    }

    @Test
    fun `wrong chars`() {
        assertEquals(checkSubstitution("abc@#kek."), false)
    }

    @Test
    fun `two terminals`() {
        assertEquals(checkSubstitution("abc#def.."), false)
    }

    // Test checkQuery =============================================================================================
    @Test
    fun `empty query`() {
        assertEquals(checkQuery(arrayOf()), true)
    }

    @Test
    fun `right query`() {
        assertEquals(checkQuery(arrayOf("abc", "ab#cd.", "01#100")), true)
    }

    @Test
    fun `wrong query`() {
        assertEquals(checkQuery(arrayOf("ab&", "ab#cd")), false)
    }

    // Test parseQuery =============================================================================================
    @Test
    fun `simple test for query parser`() {
        val a = parseQuery(arrayOf("abcd", "ab#k3k", "cd#sos."))
        val b = Query("abcd", arrayOf(Substitution("ab", "k3k", false), Substitution("cd", "sos", true)))
        assertEquals(a.mainString, b.mainString)
        assertEquals(a.substitutions.toList(), b.substitutions.toList())
    }
}