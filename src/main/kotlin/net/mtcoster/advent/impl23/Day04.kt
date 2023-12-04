package net.mtcoster.advent.impl23

import net.mtcoster.advent.parsers.IntParser
import net.mtcoster.advent.parsers.Parser
import net.mtcoster.advent.parsers.lines
import net.mtcoster.advent.util.Day

object Day04 : Day<List<Day04.Card>, Int>(2023, 4, Card.lines(), IntParser) {
    override fun solveA(input: List<Card>): Int {
        return input.sumOf {
            when (val count = it.matchingNumbers.size) {
                0 -> 0
                else -> 1 shl (count - 1)
            }
        }
    }

    override fun solveB(input: List<Card>): Int {
        val counts = IntArray(input.size) { 1 }

        for ((i, card) in input.withIndex()) {
            for (j in (i + 1)..<(i + 1 + card.matchingNumbers.size)) {
                counts[j] += counts[i]
            }
        }

        return counts.sum()
    }

    @JvmStatic fun main(args: Array<String>) = run()

    data class Card(
        @JvmField val winningNumbers: Set<Int>,
        @JvmField val numbers: Set<Int>,
    ) {
        @JvmField val matchingNumbers = numbers.asSequence().filter { it in winningNumbers }.toSet()

        companion object : Parser.String<Card> {
            private fun parseNumberList(string: String): Set<Int> {
                return string.splitToSequence(' ')
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toSet()
            }

            override fun parse(input: String): Card {
                val parts = input.substringAfter(':').split('|')

                return Card(parseNumberList(parts[0]), parseNumberList(parts[1]))
            }
        }
    }
}
