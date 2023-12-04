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


inline fun <T, K : Any> Sequence<T>.groupByNotNull(keySelector: (T) -> K?): Map<K, List<T>> =
    groupByNotNullTo(LinkedHashMap(), keySelector)

inline fun <T, K : Any, V> Sequence<T>.groupByNotNull(keySelector: (T) -> K?, valueTransform: (T) -> V): Map<K, List<V>> =
    groupByNotNullTo(LinkedHashMap(), keySelector, valueTransform)

inline fun <T, K : Any, M : MutableMap<in K, MutableList<T>>> Sequence<T>.groupByNotNullTo(destination: M, keySelector: (T) -> K?): M {
    for (element in this) {
        val key = keySelector(element) ?: continue
        val list = destination.getOrPut(key) { ArrayList() }
        list.add(element)
    }
    return destination
}

inline fun <T, K : Any, V, M : MutableMap<in K, MutableList<V>>> Sequence<T>.groupByNotNullTo(destination: M, keySelector: (T) -> K?, valueTransform: (T) -> V): M {
    for (element in this) {
        val key = keySelector(element) ?: continue
        val list = destination.getOrPut(key) { ArrayList() }
        list.add(valueTransform(element))
    }
    return destination
}
