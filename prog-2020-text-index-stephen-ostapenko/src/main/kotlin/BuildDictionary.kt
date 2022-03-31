// Adding source of a word to dictionary.
fun addSource(dict: MutableDict, source: String, form: String) {
    if (!dict.containsKey(form)) {
        dict[form] = mutableListOf()
    }
    dict[form]?.add(source)
}

// Building dictionary.
fun buildDictionary(dictionaryFileName: String = FORM_DICTIONARY_PATH): Dict {
    val dict: MutableDict = mutableMapOf()
    val lines = readLines(dictionaryFileName)
    for (word in lines) {
        val rawForms = word.split(',')
        // We need every element except the second one (the one with index 1).
        val forms = listOf(rawForms[0]) + rawForms.subList(2, rawForms.size)
        forms.distinct().forEach { form -> addSource(dict, forms[0], form) }
    }
    return dict.map { it.key to it.value.distinct() }.toMap()
}

// Fixing dictionary.
// If text contains a word which is not sated in dictionary, we add this word to dictionary.
fun fixDictionary(text: Text, dict: Dict): Dict {
    val newDict = dict.toMutableMap()
    for (line in text) {
        line.forEach { word -> if (!newDict.containsKey(word)) newDict[word] = mutableListOf(word) }
    }
    return newDict.toMap()
}