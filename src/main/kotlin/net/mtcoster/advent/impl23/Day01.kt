package net.mtcoster.advent.impl23

import net.mtcoster.advent.util.Day
import net.mtcoster.advent.util.findAllWithOverlap
import net.mtcoster.advent.util.firstAndLast
import net.mtcoster.advent.util.map
import java.io.BufferedReader

object Day01 : Day<List<String>>(2023, 1) {
    override fun processInput(input: BufferedReader): List<String> {
        return input.readLines()
    }

    override fun solveA(input: List<String>): Any {
        return input.sumOf { line ->
            val (d0, d1) = checkNotNull(line.asSequence().firstAndLast { it in DIGITS }).map { it.digitToInt() }

            d0 * 10 + d1
        }
    }

    override fun solveB(input: List<String>): Any {
        return input.sumOf { line ->
            val (d0, d1) = checkNotNull(REGEX.findAllWithOverlap(line).firstAndLast()).map { it.parseDigitOrWord() }

            d0 * 10 + d1
        }
    }

    @JvmStatic fun main(args: Array<String>) = run()

    private val DIGITS = '1'..'9'
    private val WORDS = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    private val REGEX = WORDS.joinToString("|", prefix="\\d|").toRegex()

    private fun String.isDigit() = singleOrNull() in DIGITS
    private fun MatchResult.parseDigitOrWord() = if (value.isDigit()) value.toInt() else WORDS.indexOf(value) + 1
}
