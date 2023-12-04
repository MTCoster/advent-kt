package net.mtcoster.advent.test23

import net.mtcoster.advent.testutil.DayTest
import net.mtcoster.advent.impl23.Day01


object Day01Test : DayTest<List<String>, Int>(Day01) {
    private val EXAMPLE_A = Example(
        rawInput = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent(),
        parsedInput = listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet",
        ),
        solutionA = 142,
    )

    private val EXAMPLE_B = Example(
        rawInput = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent(),
        parsedInput = listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
        ),
        solutionB = 281,
    )

    override val examples = listOf(EXAMPLE_A, EXAMPLE_B)
}
