package net.mtcoster.advent.util

import net.mtcoster.advent.parsers.Parser
import net.mtcoster.advent.parsers.trimmed
import java.io.InputStream
import java.io.Reader


fun Int.zeroPad(length: Int) = toString().padStart(length, '0')

fun String.isMultiline() = lineSequence().take(2).count() > 1


abstract class Day<I : Any, O : Any>(
    @JvmField val year: Int,
    @JvmField val day: Int,
    @JvmField val inputParser: Parser<I>,
    @JvmField val outputParser: Parser<O>,
) : Runnable {
    init {
        require(day in 1..25)
    }

    abstract fun solveA(input: I): O
    abstract fun solveB(input: I): O

    private val resourceDir = "/net/mtcoster/advent/data${(year - 2000).zeroPad(2)}/"
    private val inputFile get() = resourceDir + "input${day.zeroPad(2)}.txt"
    private val solutionFileA get() = resourceDir + "solution${day.zeroPad(2)}a.txt"
    private val solutionFileB get() = resourceDir + "solution${day.zeroPad(2)}b.txt"

    private fun openResource(path: String) = javaClass.getResourceAsStream(path)
    private fun <R> InputStream.useResource(error: String, block: (Reader) -> R) = use { input ->
        try {
            input.bufferedReader(Charsets.UTF_8).use(block)
        } catch (e: Exception) {
            print(error)
            println(e)
            throw e
        }
    }

    private fun printSolution(name: String, solve: (I) -> O, input: I, solutionFile: String) {
        val knownSolution = openResource(solutionFile)?.let { stream ->
            stream.useResource("Failed to parse output: ") {
                outputParser.trimmed().parse(it)
            }
        }

        println("--- Solution $name ---")
        try {
            val solution = solve(input)
            val solutionString = outputParser.toString(solution)
            print(outputParser.toString(solution))

            if (solutionString.isMultiline()) println()

            when (knownSolution) {
                null -> println(" – no known solution")
                solution -> println(" – matches known solution")
                else -> {
                    print(" – does NOT match known solution:")
                    val knownSolutionString = outputParser.toString(knownSolution)
                    if (knownSolutionString.isMultiline()) {
                        println()
                    } else {
                        print(" ")
                    }
                    println(knownSolutionString)
                }
            }
        } catch (e: NotImplementedError) {
            println("Not implemented.")
        }
        println()
    }

    final override fun run() {
        println("Advent of Code $year – Day ${day.zeroPad(2)}")

        val rawInput = openResource(inputFile)
        if (rawInput == null) {
            println("No input found!")
            return
        }

        val input = rawInput.useResource("Failed to parse input: ") {
            inputParser.parse(it)
        }

        println()

        printSolution("A", ::solveA, input, solutionFileA)
        printSolution("B", ::solveB, input, solutionFileB)
    }
}
