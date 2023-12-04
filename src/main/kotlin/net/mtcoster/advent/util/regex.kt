package net.mtcoster.advent.util


fun Regex.findAllWithOverlap(input: CharSequence) =
    generateSequence({ find(input) }, { find(input, it.range.first + 1) })

