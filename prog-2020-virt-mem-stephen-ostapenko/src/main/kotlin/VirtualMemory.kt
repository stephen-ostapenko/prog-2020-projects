@file: JvmName("main")

import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder
import java.util.*

// Generates correct random input.
fun genRandomInput(memSize: Int, ramSize: Int, queriesCnt: Int): List<String> {
    val answer = mutableListOf(memSize, ramSize).plus(1..ramSize).toMutableList()
    val rnd = Random()
    for (iter in 1..queriesCnt) {
        answer.add(rnd.nextInt(memSize) + 1)
    }
    return List(answer.size) { i -> answer[i].toString() }
}

fun readLines(fileName: String): List<String> {
    return File(fileName).readLines()
}

// Check that lines in input represent correct model of querying.
fun checkInput(lines: List<String>): Boolean {
    if (lines.size < 2 || !lines.none { line -> line.toIntOrNull() == null }) {
        return false
    }
    val memSize = lines[0].toInt()
    val ramSize = lines[1].toInt()
    if (memSize < ramSize || ramSize + 2 > lines.size || ramSize <= 0 || memSize <= 0) {
        return false
    }
    if (lines.filterIndexed { index, s -> index >= 2 && lines[index].toInt() !in 1..memSize }.isNotEmpty()) {
        return false
    }
    return lines.subList(2, 2 + ramSize).distinct().size == ramSize
}

// Split lines in input into initial memory and queries.
fun parseInput(lines: List<String>): Pair<MutableList<Int>, List<Int>> {
    val ramSize = lines[1].toInt()
    return MutableList(ramSize) { i -> lines[i + 2].toInt() } to
            List(lines.size - 2 - ramSize) { i -> lines[i + 2 + ramSize].toInt() }
}

// Implementation of FIFO algorithm.
// Works with queue of blocks ordered by time they were loaded to RAM.
fun processFIFO(memSize: Int, ramSize: Int, initialMemory: List<Int>, queries: List<Int>): Int {
    val currentMemory = initialMemory.toMutableList()
    // $isInCurrentMemory[q] = true if q is in RAM right now and false otherwise.
    val isInCurrentMemory = MutableList(memSize + 1) { i -> i in initialMemory }
    // $queueOfBlocks is a queue of RAM blocks ordered by the chance of replacing.
    val queueOfBlocks = LinkedList<Int>((0 until ramSize).toList())
    var answer = 0
    for (q in queries) {
        if (isInCurrentMemory[q]) {
            continue
        }
        val index = queueOfBlocks.remove()
        queueOfBlocks.add(index)
        isInCurrentMemory[currentMemory[index]] = false
        isInCurrentMemory[q] = true
        currentMemory[index] = q
        answer++
    }
    return answer
}

// Implementation of LRU algorithm.
// Works with priority queue of blocks ordered by time of the last use.
fun processLRU(ramSize: Int, initialMemory: List<Int>, queries: List<Int>): Int {
    val currentMemory = initialMemory.toMutableList()
    // $queueOfBlocks is a priority queue ordered by the chance of replacing.
    val queueOfBlocks = MutableList(ramSize) { i -> i }
    var answer = 0
    for (q in queries) {
        var index: Int
        if (q in currentMemory) {
            index = currentMemory.indexOf(q)
        } else {
            index = queueOfBlocks.first()
            currentMemory[index] = q
            answer++
        }
        queueOfBlocks.remove(index)
        queueOfBlocks.add(index)
    }
    return answer
}

// Implementation of OPT algorithm.
// Uses a list of occurrences (stack) in memory for each block.
@ExperimentalStdlibApi
fun processOPT(memSize: Int, initialMemory: List<Int>, queries: List<Int>): Int {
    val currentMemory = initialMemory.toMutableList()
    // $occurrences is a list of indexes of occurrences for each element in memory.
    val occurrences = List(memSize + 1) { mutableListOf(queries.size) }
    for (index in queries.size - 1 downTo 0) {
        val q = queries[index]
        occurrences[q].add(index)
    }
    var answer = 0
    for (q in queries) {
        if (q in currentMemory) {
            occurrences[q].removeLast()
            continue
        }
        // We use list of occurrences of each element to find out the one
        // which is the least important at the moment, so we can replace it.
        val index = currentMemory.indexOf(currentMemory.maxBy { occurrences[it].last() })
        currentMemory[index] = q
        occurrences[q].removeLast()
        answer++
    }
    return answer
}

// Checking arguments for random run.
fun checkArgsForRandom(args: Array<String>): Boolean {
    return !(args[2].toInt() < args[3].toInt() ||
            args[3].toInt() < 0 || args[4].toInt() < 0 || args[5].toInt() < 0)
}

@ExperimentalStdlibApi
fun process(memSize: Int, ramSize: Int, initialMemory: List<Int>, queries: List<Int>): String {
    val result = StringBuilder()
    print("FIFO: ")
    println(processFIFO(memSize, ramSize, initialMemory, queries))
    result.append("FIFO: ${processFIFO(memSize, ramSize, initialMemory, queries)}\n")
    print("LRU: ")
    println(processLRU(ramSize, initialMemory, queries))
    result.append("LRU: ${processLRU(ramSize, initialMemory, queries)}\n")
    print("OPT: ")
    println(processOPT(memSize, initialMemory, queries))
    result.append("OPT: ${processOPT(memSize, initialMemory, queries)}\n")
    println()
    result.append("\n")
    return result.toString()
}

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    try {
        if (args[0] != "-r" && args[0] != "-f") {
            throw IllegalArgumentException("Wrong mode option")
        }
        if ("-r" in args) {
            File(args[1]).writeText("")
            if (!checkArgsForRandom(args)) {
                throw IllegalArgumentException("Wrong args for random generator!")
            }
            for (taskNumber in 0 until args[5].toInt()) {
                val input = genRandomInput(args[2].toInt(), args[3].toInt(), args[4].toInt())
                val memSize = input[0].toInt()
                val ramSize = input[1].toInt()
                val (initialMemory, queries) = parseInput(input)
                File(args[1]).appendText(process(memSize, ramSize, initialMemory, queries))
            }
        } else {
            val input = readLines(args[1])
            if (!checkInput(input)) {
                throw IllegalArgumentException("Wrong input! See documentation for more details.")
            }
            val memSize = input[0].toInt()
            val ramSize = input[1].toInt()
            val (initialMemory, queries) = parseInput(input)
            File(args[2]).writeText(process(memSize, ramSize, initialMemory, queries))
        }
    }
    catch (e: IndexOutOfBoundsException) {
        println(e.toString())
    }
    catch (e: NumberFormatException) {
        println(e.toString())
    }
    catch (e: IllegalArgumentException) {
        println(e.toString())
    }
    catch (e: FileNotFoundException) {
        println(e.toString())
    }
    catch (e: Exception) {
        println(e.toString())
    }
}