import java.io.File

// Maximal allowed number of iterations in process.
const val MAX_ITERATIONS = 50 * 1000
// Special string to separate queries.
const val QUERY_DELIMITER = "$"
// Special char to separate substitution and replacement.
const val SUBST_DELIMITER = '#'
// Special char to indicate terminal substitution.
const val TERMINAL = '.'
// Chars that are allowed to use (digits, latin letters, minus and underscore).
val POSSIBLE_CHARS = (('0'..'9').toList() + ('A'..'Z').toList() + ('a'..'z').toList() + listOf('-', '_')).toTypedArray()

// Class for substitutions (substitution -> replacement).
// Terminal shows whether substitution is terminal or not.
data class Substitution(val substitution: String, val replacement: String, val terminal: Boolean)
// Class for queries.
data class Query(val mainString: String, val substitutions: Array<Substitution>)

// Function finds first substitution which can be applied to main string.
// If there is no such substitution, function returns -1.
fun findFirstSubstitution(mainString: String, substitutions: Array<Substitution>): Int {
    for (index in substitutions.indices) {
        if (mainString.indexOf(substitutions[index].substitution) != -1) {
            return index
        }
    }
    return -1
}

// Function applies a specified substitution to main string.
// Called only when a substitution can be applied.
fun applySubstitution(mainString: String, substitution: String, replacement: String): String {
    val index = mainString.indexOf(substitution)
    return mainString.replaceRange(index, index + substitution.length, replacement)
}

// Main procedure of the program.
// Applies the scheme to main string.
fun applyScheme(mainString: String, substitutions: Array<Substitution>): String {
    var resultString: String = mainString
    for (iteration in 0 until MAX_ITERATIONS) {
        val index = findFirstSubstitution(resultString, substitutions)
        if (index == -1) {
            return resultString
        }
        resultString = applySubstitution(resultString, substitutions[index].substitution, substitutions[index].replacement)
        if (substitutions[index].terminal) {
            break
        }
    }
    return resultString
}

// Checking arguments.
fun checkArgs(args: Array<String>): Int {
    if (args.size != 2) {
        return 1
    }
    if (!File(args[0]).exists()) {
        return 2
    }
    return 0
}

// Just reading all lines from file.
fun readFile(fileName: String): Array<String> {
    return File(fileName).readLines().toTypedArray()
}

// Split lines by special delimiter line.
fun split(lines: Array<String>): Array<Array<String>> {
    val result = mutableListOf<Array<String>>()
    val currentPart = mutableListOf<String>()
    for (line in lines) {
        if (line == QUERY_DELIMITER) {
            result.add(currentPart.toTypedArray())
            currentPart.clear()
        } else {
            currentPart.add(line)
        }
    }
    if (currentPart.size > 0) {
        result.add(currentPart.toTypedArray())
    }
    return result.toTypedArray()
}

// Function to check substitution format.
// Correct substitution: {substitution}{$SUBST_DELIMITER}{replacement}{terminal}.
// {substitution} is a string to substitute, {replacement} is a string to replace {substitution},
// {terminal} can be $TERMINAL whether the substitution is terminal and empty string otherwise.
// Default $SUBST_DELIMITER is '#'.
fun checkSubstitution(substitution: String): Boolean {
    if (substitution.isEmpty()) {
        return false
    }
    val trimmedSubstitution = if (substitution.last() == TERMINAL) substitution.dropLast(1) else substitution
    var delimitersFound = 0
    for (char in trimmedSubstitution) {
        if (char == SUBST_DELIMITER) {
            delimitersFound++
            continue
        }
        if (char !in POSSIBLE_CHARS) {
            return false
        }
    }
    if (delimitersFound != 1) {
        return false
    }
    return true
}

// Function to check query format.
fun checkQuery(lines: Array<String>): Boolean {
    if (lines.isEmpty()) {
        return true
    }
    // First line should be a main string.
    for (char in lines[0]) {
        if (char !in POSSIBLE_CHARS) {
            return false
        }
    }
    // The rest lines should be substitutions.
    for (index in 1 until lines.size) {
        if (!checkSubstitution(lines[index])) {
            return false
        }
    }
    return true
}

// Build a query from array of lines.
fun parseQuery(lines: Array<String>): Query {
    if (lines.isEmpty()) {
        return Query("", arrayOf())
    }
    val mainString = lines[0]
    val substitutions = mutableListOf<Substitution>()
    for (index in 1 until lines.size) {
        val currentSubstitution = lines[index].split(SUBST_DELIMITER)
        val terminal = (currentSubstitution[1].isNotEmpty() && currentSubstitution[1].last() == TERMINAL)
        substitutions.add(Substitution(currentSubstitution[0], currentSubstitution[1].dropLastWhile { it == TERMINAL }, terminal))
    }
    return Query(mainString, substitutions.toTypedArray())
}

// Beginning of the program.
fun main(args: Array<String>) {
    when (checkArgs(args)) {
        1 -> {
            println("Put name of input file and name of output file.")
            return
        }
        2 -> {
            println("Input file doesn't exist.")
            return
        }
    }
    val input = split(readFile(args[0]))
    var right = 0
    var wrong = 0
    File(args[1]).writeText("")
    for (index in input.indices) {
        val query = input[index]
        if (checkQuery(query)) {
            println("${index + 1}: Process started.")
            System.out.flush()
            val currentQuery = parseQuery(query)
            File(args[1]).appendText(applyScheme(currentQuery.mainString, currentQuery.substitutions) + "\n")
            println("${index + 1}: Process ended successfully.")
            right++
        } else {
            println("${index + 1}: Error! Wrong query format. See documentation for more details.")
            File(args[1]).appendText("!!!WRONG QUERY!!!\n")
            wrong++
        }
    }
    println("\n\tJobs finished: ${right + wrong}\n\tSuccessful: $right\n\tErrors: $wrong")
}