import java.io.File

// We have found a word in text. It has one ore more possible sources.
// For each of possible sources we add this occurrence to index.
fun addOccurrences(textIndex: MutableIndex, dict: Dict, lineNumber: Int, word: String) {
    dict[word]?.forEach { source ->
        if (!textIndex.containsKey(source)) {
            textIndex[source] = mutableListOf()
        }
        textIndex[source]?.add(Occurrence(lineNumber, word))
    }
}

// Building text Index.
fun buildIndex(text: Text, dict: Dict, inputFileName: String): Index {
    if (checkIndexActuality(inputFileName)) {
        return getIndexFromFile(inputFileName)
    }
    val index: MutableIndex = mutableMapOf()
    for (lineNumber in text.indices) {
        text[lineNumber].forEach { word -> addOccurrences(index, dict, lineNumber, word) }
    }
    outputIndexToStorageFile(index, inputFileName)
    return index.toMap()
}

// Checking that current index is valid for this text.
fun checkIndexActuality(inputFileName: String): Boolean {
    val inputFile = File(inputFileName)
    val indexFile = File("$inputFileName.index")
    return indexFile.exists() && indexFile.lastModified() > inputFile.lastModified()
}

// Getting earlier prepared index from file.
fun getIndexFromFile(inputFileName: String): Index {
    val index: MutableIndex = mutableMapOf()
    readLines("$inputFileName.index").forEach { line ->
        // Index is stored in the following format:
        // <source form of word>:<line number in text>:<form of word in text>
        val (sourceForm, lineNumber, formInText) = line.split(":")
        if (lineNumber.toIntOrNull() == null) {
            throw IllegalStateException("Index is broken (index is stored in file .$inputFileName.index)")
        }
        if (!index.containsKey(sourceForm)) {
            index[sourceForm] = mutableListOf()
        }
        index[sourceForm]?.add(Occurrence(lineNumber.toInt(), formInText))
    }
    return index.toMap()
}

// Printing index into file to store it for future.
fun outputIndexToStorageFile(index: Index, inputFileName: String) {
    File("$inputFileName.index").writeText("")
    writeLine("$inputFileName.index", getIndex(index))
}