package net.mtcoster.advent.impl23

import net.mtcoster.advent.parsers.IntParser
import net.mtcoster.advent.parsers.Parser
import net.mtcoster.advent.parsers.lines
import net.mtcoster.advent.util.Day
import kotlin.math.max

object Day02 : Day<List<Day02.Game>, Int>(2023, 2, Game.lines(), IntParser) {
    override fun solveA(input: List<Game>): Int {
        val max = Round(12, 13, 14)

        return input.asSequence()
            .withIndex()
            .filter { it.value.canPlayWith(max) }
            .sumOf { it.index + 1 }
    }

    override fun solveB(input: List<Game>): Int {
        return input.sumOf { it.rounds.reduce(::maxOf).run { red * green * blue } }
    }

    @JvmStatic fun main(args: Array<String>) = run()

    data class Game(@JvmField val rounds: List<Round>) {
        constructor(vararg rounds: Round) : this(rounds.toList())

        fun canPlayWith(max: Round) = rounds.all { it.canPlayWith(max) }

        companion object : Parser.String<Game> {
            override fun parse(input: String): Game {
                val rounds = input.substringAfter(':')
                    .splitToSequence(';')
                    .map { Round.parse(it) }
                    .toList()

                return Game(rounds)
            }
        }
    }

    data class Round(
        @JvmField val red: Int = 0,
        @JvmField val green: Int = 0,
        @JvmField val blue: Int = 0,
    ) {
        fun canPlayWith(max: Round) = red <= max.red && green <= max.green && blue <= max.blue

        companion object : Parser.String<Round> {
            override fun parse(input: String): Round {
                var red = 0
                var green = 0
                var blue = 0

                val parts = input.splitToSequence(',', ' ')
                    .filter { it.isNotEmpty() }
                    .windowed(2, 2)

                for ((count, color) in parts) {
                    when (color) {
                        "red" -> red = count.toInt()
                        "green" -> green = count.toInt()
                        "blue" -> blue = count.toInt()
                        else -> throw IllegalArgumentException("Unknown color '$color'")
                    }

                }

                return Round(red, green, blue)
            }
        }
    }

    fun maxOf(a: Round, b: Round) = Round(
        max(a.red, b.red),
        max(a.green, b.green),
        max(a.blue, b.blue),
    )
}
