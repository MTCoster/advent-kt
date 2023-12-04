package net.mtcoster.advent.test23

import net.mtcoster.advent.testutil.DayTest
import net.mtcoster.advent.impl23.Day03
import net.mtcoster.advent.impl23.Day03.Input
import net.mtcoster.advent.impl23.Day03.Number
import net.mtcoster.advent.impl23.Day03.WidePos
import net.mtcoster.advent.util.CharArrayGrid2D


object Day03Test : DayTest<Input, Int>(Day03) {
    private val EXAMPLE = Example(
        rawInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent(),
        parsedInput = Input(
            CharArrayGrid2D(0..<10, 0..<10, charArrayOf(
                '4', '6', '7', '.', '.', '1', '1', '4', '.', '.',
                '.', '.', '.', '*', '.', '.', '.', '.', '.', '.',
                '.', '.', '3', '5', '.', '.', '6', '3', '3', '.',
                '.', '.', '.', '.', '.', '.', '#', '.', '.', '.',
                '6', '1', '7', '*', '.', '.', '.', '.', '.', '.',
                '.', '.', '.', '.', '.', '+', '.', '5', '8', '.',
                '.', '.', '5', '9', '2', '.', '.', '.', '.', '.',
                '.', '.', '.', '.', '.', '.', '7', '5', '5', '.',
                '.', '.', '.', '$', '.', '*', '.', '.', '.', '.',
                '.', '6', '6', '4', '.', '5', '9', '8', '.', '.',
            )),
            listOf(
                Number(WidePos(0..2, 0), 467),
                Number(WidePos(5..7, 0), 114),
                Number(WidePos(2..3, 2), 35),
                Number(WidePos(6..8, 2), 633),
                Number(WidePos(0..2, 4), 617),
                Number(WidePos(7..8, 5), 58),
                Number(WidePos(2..4, 6), 592),
                Number(WidePos(6..8, 7), 755),
                Number(WidePos(1..3, 9), 664),
                Number(WidePos(5..7, 9), 598),
            ),
        ),
        solutionA = 4361,
        solutionB = 467835,
    )

    override val examples = listOf(EXAMPLE)
}
