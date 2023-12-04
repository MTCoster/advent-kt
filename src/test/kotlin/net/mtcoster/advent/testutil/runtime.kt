package net.mtcoster.advent.testutil

import net.mtcoster.advent.util.Day
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.condition.EnabledIf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DayTest<I : Any, O : Any>(protected val day: Day<I, O>)  {
    open val examples: List<Example<I, O>> get() = emptyList()

    private fun allExamples() = examples.stream()
    private fun examplesWithParsedInput() = examples.stream().filter { it.parsedInput != null }
    private fun examplesWithA() = examples.stream().filter { it.solutionA != null }
    private fun examplesWithB() = examples.stream().filter { it.solutionB != null }

    private fun hasExamples() = examples.isNotEmpty()
    private fun hasExamplesWithParsedInput() = examples.any { it.parsedInput != null }
    private fun hasExamplesWithA() = examples.any { it.solutionA != null }
    private fun hasExamplesWithB() = examples.any { it.solutionB != null }

    @ParameterizedTest
    @EnabledIf("hasExamplesWithParsedInput")
    @MethodSource("examplesWithParsedInput")
    fun `process example input`(example: Example<I, O>) {
        assertEquals(example.parsedInput, day.inputParser.parse(example.rawInput))
    }

    @ParameterizedTest
    @EnabledIf("hasExamplesWithA")
    @MethodSource("examplesWithA")
    fun `solve a`(example: Example<I, O>) {
        assertEquals(example.solutionA, day.solveA(example.parseInput(day)))
    }

    @ParameterizedTest
    @EnabledIf("hasExamplesWithB")
    @MethodSource("examplesWithB")
    fun `solve b`(example: Example<I, O>) {
        assertEquals(example.solutionB, day.solveB(example.parseInput(day)))
    }

    data class Example<I : Any, O : Any>(
        @JvmField val rawInput: String,
        @JvmField val parsedInput: I? = null,
        @JvmField val solutionA: O? = null,
        @JvmField val solutionB: O? = null,
    ) {
        private var ourParsedInput: I? = null

        fun parseInput(day: Day<I, O>): I {
            return ourParsedInput ?: (parsedInput ?: day.inputParser.parse(rawInput)).also { ourParsedInput = it }
        }
    }
}
