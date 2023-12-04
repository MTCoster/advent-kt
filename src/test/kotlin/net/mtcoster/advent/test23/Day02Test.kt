package net.mtcoster.advent.test23

import net.mtcoster.advent.testutil.DayTest
import net.mtcoster.advent.impl23.Day02
import net.mtcoster.advent.impl23.Day02.Game
import net.mtcoster.advent.impl23.Day02.Round


object Day02Test : DayTest<List<Game>, Int>(Day02) {
    private val EXAMPLE = Example(
        rawInput = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent(),
        parsedInput = listOf(
            Game(Round(blue = 3, red = 4), Round(red = 1, green = 2, blue = 6), Round(green = 2)),
            Game(Round(blue = 1, green = 2), Round(green = 3, blue = 4, red = 1), Round(green = 1, blue = 1)),
            Game(Round(green = 8, blue = 6, red = 20), Round(blue = 5, red = 4, green = 13), Round(green = 5, red = 1)),
            Game(Round(green = 1, red = 3, blue = 6), Round(green = 3, red = 6), Round(green = 3, blue = 15, red = 14)),
            Game(Round(red = 6, blue = 1, green = 3), Round(blue = 2, red = 1, green = 2)),
        ),
        solutionA = 8,
        solutionB = 2286,
    )

    override val examples = listOf(EXAMPLE)
}
