package net.mtcoster.advent.util

val IntRange.size get() = if (isEmpty()) 0 else last - first + 1
