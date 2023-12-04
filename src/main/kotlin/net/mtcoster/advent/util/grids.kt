package net.mtcoster.advent.util

interface Grid2D<T> {
    val xRange: IntRange
    val yRange: IntRange

    operator fun contains(pos: Pos2D) = pos.x in xRange && pos.y in yRange

    operator fun get(pos: Pos2D) = get(pos.x, pos.y)
    operator fun get(x: Int, y: Int): T

    fun getOrNull(pos: Pos2D) = getOrNull(pos.x, pos.y)
    fun getOrNull(x: Int, y: Int): T?
}

interface MutableGrid2D<T> : Grid2D<T> {
    operator fun set(pos: Pos2D, value: T) = set(pos.x, pos.y, value)
    operator fun set(x: Int, y: Int, value: T)
}


class ArrayGrid2D<T>(
    override val xRange: IntRange,
    override val yRange: IntRange,
    private val storage: Array<T>,
) : MutableGrid2D<T> {
    init {
        check(storage.size == xRange.size * yRange.size)
    }

    override fun get(x: Int, y: Int): T {
        require(x in xRange)
        require(y in yRange)
        return storage[arrayIndex(x, y, xRange, yRange)]
    }

    override fun getOrNull(x: Int, y: Int): T? {
        if (x !in xRange) return null
        if (y !in yRange) return null
        return storage[arrayIndex(x, y, xRange, yRange)]
    }

    override fun set(x: Int, y: Int, value: T) {
        require(x in xRange)
        require(y in yRange)
        storage[arrayIndex(x, y, xRange, yRange)] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArrayGrid2D<*>

        if (xRange != other.xRange) return false
        if (yRange != other.yRange) return false
        if (!storage.contentEquals(other.storage)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xRange.hashCode()
        result = 31 * result + yRange.hashCode()
        result = 31 * result + storage.contentHashCode()
        return result
    }

    companion object {
        @JvmName("fromRowsInternal")
        inline fun <reified T, R> fromRows(
            rows: Collection<R>,
            xMin: Int,
            yMin: Int,
            crossinline rowSize: (row: R) -> Int,
            crossinline copyRow: (row: R, storage: Array<in T>, offset: Int) -> Unit,
        ): ArrayGrid2D<T> {
            val width = rowSize(rows.iterator().next())
            val height = rows.size

            val storage = arrayOfNulls<T>(width * height)

            for ((y, row) in rows.withIndex()) {
                require(rowSize(row) == width)

                copyRow(row, storage, width * y)
            }

            @Suppress("UNCHECKED_CAST")
            return ArrayGrid2D(xMin..<xMin + width, yMin..<yMin + height, storage as Array<T>)
        }

        @JvmName("fromArrayRows")
        inline fun <reified T> fromRows(rows: Collection<Array<T>>, xMin: Int = 0, yMin: Int = 0) =
            fromRows<T, Array<T>>(rows, xMin, yMin, { it.size }) { row, storage, offset ->
                row.copyInto(storage, offset)
            }

        @JvmName("fromListRows")
        inline fun <reified T> fromRows(rows: Collection<List<T>>, xMin: Int = 0, yMin: Int = 0) =
            fromRows<T, List<T>>(rows, xMin, yMin, { it.size }) { row, storage, offset ->
                for ((i, it) in row.withIndex()) {
                    storage[offset + i] = it
                }
            }
    }
}

class CharArrayGrid2D(
    override val xRange: IntRange,
    override val yRange: IntRange,
    private val storage: CharArray,
) : MutableGrid2D<Char> {
    init {
        check(storage.size == xRange.size * yRange.size)
    }

    override fun get(x: Int, y: Int): Char {
        require(x in xRange)
        require(y in yRange)
        return storage[arrayIndex(x, y, xRange, yRange)]
    }

    override fun getOrNull(x: Int, y: Int): Char? {
        if (x !in xRange) return null
        if (y !in yRange) return null
        return storage[arrayIndex(x, y, xRange, yRange)]
    }

    override fun set(x: Int, y: Int, value: Char) {
        require(x in xRange)
        require(y in yRange)
        storage[arrayIndex(x, y, xRange, yRange)] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharArrayGrid2D

        if (xRange != other.xRange) return false
        if (yRange != other.yRange) return false
        if (!storage.contentEquals(other.storage)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xRange.hashCode()
        result = 31 * result + yRange.hashCode()
        result = 31 * result + storage.contentHashCode()
        return result
    }

    companion object {
        private inline fun <R> fromRows(
            rows: Collection<R>,
            xMin: Int,
            yMin: Int,
            crossinline rowSize: (row: R) -> Int,
            crossinline copyRow: (row: R, storage: CharArray, offset: Int) -> Unit,
        ): CharArrayGrid2D {
            val width = rowSize(rows.iterator().next())
            val height = rows.size

            val storage = CharArray(width * height)

            for ((y, row) in rows.withIndex()) {
                require(rowSize(row) == width)

                copyRow(row, storage, width * y)
            }

            return CharArrayGrid2D(xMin..<xMin + width, yMin..<yMin + height, storage)
        }

        @JvmName("fromCharArrayRows")
        fun fromRows(rows: Collection<CharArray>, xMin: Int = 0, yMin: Int = 0) =
            fromRows(rows, xMin, yMin, { it.size }) { row, storage, offset ->
                row.copyInto(storage, offset)
            }

        @JvmName("fromListRows")
        fun fromRows(rows: Collection<List<Char>>, xMin: Int = 0, yMin: Int = 0) =
            fromRows(rows, xMin, yMin, { it.size }) { row, storage, offset ->
                for ((i, it) in row.withIndex()) {
                    storage[offset + i] = it
                }
            }

        @JvmName("fromStringRows")
        fun fromRows(rows: Collection<String>, xMin: Int = 0, yMin: Int = 0) =
            fromRows(rows, xMin, yMin, { it.length }) { row, storage, offset ->
                row.toCharArray(storage, offset)
            }
    }
}

class IntArrayGrid2D(
    override val xRange: IntRange,
    override val yRange: IntRange,
    private val storage: IntArray,
) : MutableGrid2D<Int> {
    init {
        check(storage.size == xRange.size * yRange.size)
    }

    override fun get(x: Int, y: Int): Int {
        require(x in xRange)
        require(y in yRange)
        return storage[arrayIndex(x, y, xRange, yRange)]
    }

    override fun getOrNull(x: Int, y: Int): Int? {
        if (x !in xRange) return null
        if (y !in yRange) return null
        return storage[arrayIndex(x, y, xRange, yRange)]
    }

    override fun set(x: Int, y: Int, value: Int) {
        require(x in xRange)
        require(y in yRange)
        storage[arrayIndex(x, y, xRange, yRange)] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IntArrayGrid2D

        if (xRange != other.xRange) return false
        if (yRange != other.yRange) return false
        if (!storage.contentEquals(other.storage)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xRange.hashCode()
        result = 31 * result + yRange.hashCode()
        result = 31 * result + storage.contentHashCode()
        return result
    }

    companion object {
        private inline fun <R> fromRows(
            rows: Collection<R>,
            xMin: Int,
            yMin: Int,
            crossinline rowSize: (row: R) -> Int,
            crossinline copyRow: (row: R, storage: IntArray, offset: Int) -> Unit,
        ): IntArrayGrid2D {
            val width = rowSize(rows.iterator().next())
            val height = rows.size

            val storage = IntArray(width * height)

            for ((y, row) in rows.withIndex()) {
                require(rowSize(row) == width)

                copyRow(row, storage, width * y)
            }

            return IntArrayGrid2D(xMin..<xMin + width, yMin..<yMin + height, storage)
        }

        @JvmName("fromIntArrayRows")
        fun fromRows(rows: Collection<IntArray>, xMin: Int = 0, yMin: Int = 0) =
            fromRows(rows, xMin, yMin, { it.size }) { row, storage, offset ->
                row.copyInto(storage, offset)
            }

        @JvmName("fromListRows")
        fun fromRows(rows: Collection<List<Int>>, xMin: Int = 0, yMin: Int = 0) =
            fromRows(rows, xMin, yMin, { it.size }) { row, storage, offset ->
                for ((i, it) in row.withIndex()) {
                    storage[offset + i] = it
                }
            }
    }
}

private fun arrayIndex(x: Int, y: Int, xRange: IntRange, yRange: IntRange) =
    (y - yRange.first) * xRange.size + (x - xRange.first)
