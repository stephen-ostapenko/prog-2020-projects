// Throw all empty strings out of input.
fun filterInput(input: List<String>): List<String> {
    return input.filter { s -> s.split(*DELIMITERS).any { it.isNotEmpty() } }
}

// Build a Text from input.
fun parseInput(input: List<String>): Text {
    val result = mutableListOf<List<String>>()
    for (line in input) {
        val current = line.toLowerCase().split(*DELIMITERS).filter { s -> s.isNotEmpty() }
        result.add(current)
    }
    return result.filter { s -> s.isNotEmpty() }.toList()
}