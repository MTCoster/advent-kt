package net.mtcoster.advent.test23

import net.mtcoster.advent.testutil.DayTest
import net.mtcoster.advent.impl23.Day04
import net.mtcoster.advent.impl23.Day04.Card


object Day04Test : DayTest<List<Card>, Int>(Day04) {
    private val EXAMPLE = Example(
        rawInput = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent(),
        parsedInput = listOf(
            Card(setOf(41, 48, 83, 86, 17), setOf(83, 86,  6, 31, 17,  9, 48, 53)),
            Card(setOf(13, 32, 20, 16, 61), setOf(61, 30, 68, 82, 17, 32, 24, 19)),
            Card(setOf( 1, 21, 53, 59, 44), setOf(69, 82, 63, 72, 16, 21, 14,  1)),
            Card(setOf(41, 92, 73, 84, 69), setOf(59, 84, 76, 51, 58,  5, 54, 83)),
            Card(setOf(87, 83, 26, 28, 32), setOf(88, 30, 70, 12, 93, 22, 82, 36)),
            Card(setOf(31, 18, 13, 56, 72), setOf(74, 77, 10, 23, 35, 67, 36, 11)),
        ),
        solutionA = 13,
        solutionB = 30,
    )

    override val examples = listOf(EXAMPLE)
}
