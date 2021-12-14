package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input11.txt").readLines()
    .map { it.toCharArray().map { ch -> ch.digitToInt() }.toMutableList() }.toMutableList()
val squidMap = input.toMutableList().map { it.toMutableList() }
private var flashedAlready = mutableSetOf<Pair<Int, Int>>()
private var flashes = 0


fun main() {
    val day = Day11().solve()
    println(day[0])
    println(day[1])
}

class Day11 {

    fun solve(): IntArray {
        val res = IntArray(2)
        var cycle = 1
        while (true) {
            for (y in squidMap.indices) {
                for (x in squidMap[y].indices) {
                    squidMap[y][x]++
                }
            }
            for (y in squidMap.indices) {
                for (x in squidMap[y].indices) {
                    if (squidMap[y][x] == 10) flash(y, x)
                }
            }
            if (cycle == 100) res[0] = flashes
            if (flashedAlready.size == 100) {
                res[1] = cycle
                return res
            }
            flashedAlready.clear()
            cycle++
        }
    }

    //simple life of game, nothing difficult to be explained

    private fun flash(y: Int, x: Int) {
        flashedAlready.add(Pair(y, x))
        squidMap[y][x] = 0
        flashes++
        for (height in (y - 1)..(y + 1)) {
            for (width in (x - 1)..(x + 1)) {
                if (squidMap.getOrNull(height)?.getOrNull(width) != null && (height != y || width != x) && squidMap[height][width] != 0 && squidMap[height][width] != 10) {
                    squidMap[height][width]++
                    if (squidMap[height][width] == 10) flash(height, width)
                }
            }
        }
    }
}