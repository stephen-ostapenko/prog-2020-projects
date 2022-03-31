import java.io.File
import java.lang.IllegalArgumentException

fun runConsole(args: Array<String>, input: List<String>, dict: Dict, index: Index, initialLineNumbers: List<Int>) {
    when (args[0]) {
        "-x" -> print(getIndex(index))
        "-f" -> writeLine("${args[1]}.index", getIndex(index))
        "-n" -> print(getNMostFrequent(args[2].toInt(), index))
        "-i" -> {
            args.forEachIndexed { ind, s -> if (ind >= 2)
                print(getWordInfo(s.toLowerCase(), index, dict, initialLineNumbers))
            }
        }
        "-t" -> {
            args.forEachIndexed { ind, s -> if (ind >= 2)
                print(getWordsByTag(s.toLowerCase(), index, dict, initialLineNumbers))
            }
        }
        "-w" -> {
            args.forEachIndexed { ind, s -> if (ind >= 2)
                print(getWordOccurrences(s.toLowerCase(), input, index, dict, initialLineNumbers))
            }
        }
        else -> throw IllegalArgumentException("Wrong key option")
    }
}

fun runFile(args: Array<String>, input: List<String>, dict: Dict, index: Index, initialLineNumbers: List<Int>) {
    File(args[2]).writeText("")
    when (args[0]) {
        "-xo" -> writeLine(args[2], getIndex(index))
        "-no" -> writeLine(args[2], getNMostFrequent(args[3].toInt(), index))
        "-io" -> {
            args.forEachIndexed { ind, s -> if (ind >= 3)
                writeLine(args[2], getWordInfo(s.toLowerCase(), index, dict, initialLineNumbers))
            }
        }
        "-to" -> {
            args.forEachIndexed { ind, s -> if (ind >= 3)
                writeLine(args[2], getWordsByTag(s.toLowerCase(), index, dict, initialLineNumbers))
            }
        }
        "-wo" -> {
            args.forEachIndexed { ind, s -> if (ind >= 3)
                writeLine(args[2], getWordOccurrences(s.toLowerCase(), input, index, dict, initialLineNumbers))
            }
        }
        else -> throw IllegalArgumentException("Wrong key option")
    }
}