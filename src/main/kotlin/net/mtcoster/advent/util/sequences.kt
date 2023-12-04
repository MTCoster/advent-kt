package net.mtcoster.advent.util

fun <T> Sequence<T>.firstAndLast(nullOnSingle: Boolean = false): Pair<T, T>? {
    val iter = iterator()
    if (!iter.hasNext()) return null

    val first = iter.next()

    if (!iter.hasNext()) {
        return if (nullOnSingle) null else first to first
    }

    val last = Sequence { iter }.last()

    return first to last
}

fun <T> Sequence<T>.firstAndLast(nullOnSingle: Boolean = false, predicate: (T) -> Boolean) =
    filter(predicate).firstAndLast(nullOnSingle)
