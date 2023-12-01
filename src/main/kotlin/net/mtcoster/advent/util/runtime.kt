package net.mtcoster.advent.util

import java.io.BufferedReader
import java.util.*


fun Int.toFixedLengthString(length: Int, padChar: Char = '0') = toString().padStart(length, padChar)


abstract class Day<I : Any>(@JvmField val year: Int, @JvmField val day: Int) : Runnable {
    init {
        require(day in 1..25)
    }

    abstract fun processInput(input: BufferedReader): I

    abstract fun solveA(input: I): Any
    abstract fun solveB(input: I): Any

    private val inputPath get() = RESOURCE_FORMAT.format(
        Locale.ROOT,
        (year - 2000).toFixedLengthString(2),
        day.toFixedLengthString(2),
    )

    final override fun run() {
        println("Advent of Code $year – Day ${day.toFixedLengthString(2)}")

        val rawInput = javaClass.getResourceAsStream(inputPath)
        if (rawInput == null) {
            println("No input found!")
            return
        }

        val input = try {
            processInput(rawInput.bufferedReader(Charsets.UTF_8))
        } catch (e: Exception) {
            print("Failed to process input: ")
            println(e)
            throw e
        }

        println()

        println("--- Solution A ---")
        try {
            println(solveA(input))
        } catch (e: NotImplementedError) {
            println("Not implemented.")
        }
        println()

        println("--- Solution B ---")
        try {
            println(solveB(input))
        } catch (e: NotImplementedError) {
            println("Not implemented.")
        }
        println()
    }

    companion object {
        private const val RESOURCE_FORMAT = "/net/mtcoster/advent/input%s/input%s.txt"
    }
}
