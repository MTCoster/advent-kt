package net.mtcoster.advent.util

import kotlin.math.abs

data class Pos2D(@JvmField val x: Int, @JvmField val y: Int) {
    companion object {
        @JvmField val ORIGIN = Pos2D(0, 0)
    }
}
