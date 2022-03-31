@file: JvmName("main")

import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException

// For each word we store list of possible sources of this word.
// Source is an initial form of the word.
// For example "яблоко" is an initial form of the word "яблоками".
typealias Dict = Map<String, List<String>>
typealias MutableDict = MutableMap<String, MutableList<String>>

// For each source we store list of possible occurrences.
typealias Index = Map<String, List<Occurrence>>
typealias MutableIndex = MutableMap<String, MutableList<Occurrence>>

// Text representation. Text is a list of lines. Each line is a list of words.
// We don't store any delimiters. Each letter is lowercase.
typealias Text = List<List<String>>

// Path to dictionary with word forms.
const val FORM_DICTIONARY_PATH = "dict/formDict"
// Path to dictionary with word tags.
const val TAG_DICTIONARY_PATH = "dict/tagDict"

// Number of strings on each page.
const val STRINGS_ON_PAGE_CNT = 45

// Magic constant ))).
// Used for text formatting.
// We consider the file can't store greater or equal to 10^8 strings.
// So line numbers are no longer than 8 symbols.
const val MAX_SIZE_OF_TEXT = 8

// Sequences used to separate words in the text.
val DELIMITERS = arrayOf(" ", ".", ",", "!", "?", ":", ";", "…", "\"", "--",
                        " - ", "—", "(", ")", "[", "]", "{", "}", "«", "»")

// Occurrence in text.
data class Occurrence(val lineNumber: Int, val form: String)

fun readLines(fileName: String): List<String> {
    return File(fileName).readLines()
}

fun writeLine(fileName: String, toWrite: String) {
    File(fileName).appendText(toWrite)
}

fun main(args: Array<String>) {
    try {
        var input = readLines(args[1])
        // Since we delete all empty strings,
        // we have to store the initial line number for each string in text.
        // It is used for correct representation of occurrences.
        // For example "word ... found in line ...".
        val initialLineNumbers = input.indices.filter { ind -> input[ind].split(*DELIMITERS).any { it.isNotEmpty() } }
        input = filterInput(input)
        val text = parseInput(input)
        val dict = fixDictionary(text, buildDictionary())
        val index = buildIndex(text, dict, args[1])
        if ("o" in args[0]) {
            runFile(args, input, dict, index, initialLineNumbers)
        } else {
            runConsole(args, input, dict, index, initialLineNumbers)
        }
    }
    catch (e: IndexOutOfBoundsException) {
        println(e.toString())
        println("Not enough arguments (see README.md)")
    }
    catch (e: FileNotFoundException) {
        println(e.toString())
        println("Input file or dictionary file are not found")
    }
    catch (e: IllegalArgumentException) {
        println(e.toString())
        println("Wrong arguments (see README.md)")
    }
    catch (e: IllegalStateException) {
        println(e.toString())
        println("Problem with text index file")
    }
    catch (e: Exception) {
        println(e.toString())
        println("Something went completely wrong")
    }
}