import java.lang.StringBuilder
import kotlin.math.min

// Function to print text Index.
fun getIndex(index: Index): String {
    val result = StringBuilder()
    for ((source, occurrences) in index) {
        occurrences.forEach { occurrence ->
            result.append("$source:${occurrence.lineNumber}:${occurrence.form}\n")
        }
    }
    return result.toString()
}

// Find and print N most frequently used words in Text.
// Here we take in count all possible forms of the word.
fun getNMostFrequent(n: Int, index: Index): String {
    val result = StringBuilder()
    val listOfMostFrequentWords = mutableListOf<Pair<Int, String>>()
    for ((source, occurrences) in index) {
        listOfMostFrequentWords.add(occurrences.size to source)
    }
    listOfMostFrequentWords.sortByDescending { it.first }
    for (ind in 0 until min(n, listOfMostFrequentWords.size)) {
        result.append("\t${listOfMostFrequentWords[ind].second}; " +
                "${listOfMostFrequentWords[ind].first} occurrence(s)\n")
    }
    return result.toString()
}

// Print information about a single word: all possible sources and list of occurrences for each word.
fun getWordInfo(word: String, index: Index, dict: Dict, initialLineNumbers: List<Int>): String {
    val result = StringBuilder()
    result.append("Word \"$word\" could be:\n")
    var anySource = ""
    dict[word]?.forEach { source ->
        result.append("$source\n")
        anySource = source
    }
    if (index[anySource].isNullOrEmpty()) {
        result.append("\nNo matches!\n\n")
        return result.toString()
    }
    result.append("\nFound ${index[anySource]?.size} occurrence(s):\n")
    index[anySource]?.forEach { occurrence ->
        result.append("\tline ${initialLineNumbers[occurrence.lineNumber] + 1}; " +
                "page ${occurrence.lineNumber / STRINGS_ON_PAGE_CNT + 1}; ${occurrence.form}\n")
    }
    result.append("\n")
    return result.toString()
}

// Function to find words in text by specified tag.
// For this purpose we use tag dictionary.
// At first we find all words in dictionary with this tag.
// Then for each word we check if this word appears in text at least once in any form.
// Then we print information about each of this words.
fun getWordsByTag(tag: String, index: Index, dict: Dict, initialLineNumbers: List<Int>, dictionaryFileName: String = TAG_DICTIONARY_PATH): String {
    val result = StringBuilder()
    val wordsByTag = readLines(dictionaryFileName).filter { it.indexOf(tag) != -1 }.map { it.split(' ')[0] }
    val foundWords = mutableListOf<String>()
    for (wordToFind in wordsByTag.filter { !dict[it].isNullOrEmpty() }) {
        dict[wordToFind]?.forEach { source -> if (!index[source].isNullOrEmpty()) foundWords.add(wordToFind) }
    }
    for (word in foundWords.distinct()) {
        result.append(getWordInfo(word, index, dict, initialLineNumbers))
    }
    return result.toString()
}

// Function to find all occurrences of a specified word and print strings with occurrences.
fun getWordOccurrences(word: String, input: List<String>, index: Index, dict: Dict, initialLineNumbers: List<Int>): String {
    val result = StringBuilder()
    var lineNumbers = mutableListOf<Int>()
    dict[word]?.forEach { source ->
        index[source]?.forEach { occurrence -> lineNumbers.add(occurrence.lineNumber) }
    }
    lineNumbers.sort()
    lineNumbers = lineNumbers.distinct().toMutableList()
    result.append("Possible occurrences for $word:\n")
    for (lineNumber in lineNumbers) {
        val ind = "${initialLineNumbers[lineNumber] + 1}".padStart(MAX_SIZE_OF_TEXT, ' ')
        result.append("$ind: ")
        result.append("${input[lineNumber]}\n")
        if ((lineNumber + 1) !in lineNumbers) {
            result.append("".padEnd(MAX_SIZE_OF_TEXT, ' ') + " ...\n")
        }
    }
    result.append("\n")
    return result.toString()
}