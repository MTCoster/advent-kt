package net.mtcoster.advent.impl23

import net.mtcoster.advent.parsers.IntParser
import net.mtcoster.advent.parsers.Parser
import net.mtcoster.advent.util.CharArrayGrid2D
import net.mtcoster.advent.util.Day
import net.mtcoster.advent.util.Pos2D
import net.mtcoster.advent.util.groupByNotNull

object Day03 : Day<Day03.Input, Int>(2023, 3, Input, IntParser) {
    override fun solveA(input: Input): Int {
        return input.numbers.asSequence()
            .filter { num -> num.pos.around().any { input.grid.getOrNull(it)?.isPart() == true } }
            .sumOf { it.value }
    }

    override fun solveB(input: Input): Int {
        return input.numbers.asSequence()
            .groupByNotNull { num -> num.pos.around().singleOrNull { input.grid.getOrNull(it) == GEAR } }
            .values.asSequence()
            .filter { it.size == 2 }
            .sumOf { it[0].value * it[1].value }
    }

    @JvmStatic fun main(args: Array<String>) = run()

    val NUMBER_REGEX = Regex("\\d+")
    const val GEAR = '*'
    fun Char.isPart() = this != '.' && this !in '0'..'9'

    data class Number(@JvmField val pos: WidePos, @JvmField val value: Int)

    data class WidePos(@JvmField val xRange: IntRange, @JvmField val y: Int) {
        fun around() = sequence {
            yieldAll(xRange.asSequence().map { Pos2D(it, y - 1) })
            yield(Pos2D(xRange.last + 1, y - 1))
            yield(Pos2D(xRange.last + 1, y))
            yield(Pos2D(xRange.last + 1, y + 1))
            yieldAll(xRange.asSequence().map { Pos2D(it, y + 1) })
            yield(Pos2D(xRange.first - 1, y + 1))
            yield(Pos2D(xRange.first - 1, y))
            yield(Pos2D(xRange.first - 1, y - 1))
        }
    }

    data class Input(@JvmField val grid: CharArrayGrid2D, @JvmField val numbers: List<Number>) {
        companion object : Parser.ByLines<Input> {
            override fun parseLines(lines: Sequence<String>): Input {
                val gridRows = lines.toList()
                val numbers = gridRows.asSequence()
                    .flatMapIndexed { y, line ->
                        NUMBER_REGEX.findAll(line).map { Number(WidePos(it.range, y), it.value.toInt()) }
                    }
                    .toList()

                return Input(CharArrayGrid2D.fromRows(gridRows), numbers)
            }
        }
    }
}
