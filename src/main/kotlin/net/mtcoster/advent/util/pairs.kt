package net.mtcoster.advent.util

@JvmName("mapPair")
fun <T, R> Pair<T, T>.map(transform: (T) -> R): Pair<R, R> = transform(first) to transform(second)
