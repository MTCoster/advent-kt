package net.mtcoster.advent.impl23

import net.mtcoster.advent.util.Day
import java.io.BufferedReader

object Day01 : Day<List<String>>(2023, 1) {
    override fun processInput(input: BufferedReader): List<String> {
        return input.readLines()
    }

    override fun solveA(input: List<String>): Any {
        return input.sumOf { line ->
            val first = line.first { it in DIGITS }
            val second = line.lastOrNull { it in DIGITS } ?: first

            first.digitToInt() * 10 + (second.digitToInt())
        }
    }

    override fun solveB(input: List<String>): Any {
        return input.sumOf { line ->
            val i0 = line.indexOfFirst { it in DIGITS }
            val i1 = line.indexOfLast { it in DIGITS }

            val w0 = WORD_REGEX.find(line)
            val w1 = WORD_REGEX_REV.find(line.reversed())

            val first = if (w0 == null || i0 in 0..w0.range.first) {
                line[i0].digitToInt()
            } else {
                WORDS.indexOf(w0.value) + 1
            }

            val second = if (w1 == null || i1 >= (line.length - w1.range.first)) {
                check(i1 >= 0)
                line[i1].digitToInt()
            } else {
                WORDS.indexOf(w1.value.reversed()) + 1
            }

            first * 10 + second
        }
    }

    @JvmStatic fun main(args: Array<String>) = run()

    private val DIGITS = '1'..'9'
    private val WORDS = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    private val WORD_REGEX = WORDS.joinToString("|").toRegex()
    private val WORD_REGEX_REV = WORDS.joinToString("|") { it.reversed() }.toRegex()
}
