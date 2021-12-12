package y2021

import java.io.File

private val inputTemp = File("./src/main/kotlin/inputs/input09.txt").readLines()
    .map { it.toCharArray().map { i -> Character.getNumericValue(i) } }
private var input = Array(inputTemp.size + 2) { IntArray(inputTemp[0].size + 2) { 9 } } // creates 2D array with borders
private val allTiles = mutableSetOf<Pair<Int, Int>>()
private var allBasins = mutableMapOf<Pair<Int, Int>, Int>()

fun main() {
    val day = Day09()
    day.fillEmpty2DArray()
    println(day.part1())
    println(day.part2())
}

class Day09 {
    fun fillEmpty2DArray() {
        for (y in 1..input.size - 2) {
            for (x in 1..input[y].size - 2) {
                input[y][x] = inputTemp[y - 1][x - 1]
            }
        }
    }

    fun part1(): Int {
        var res = 0
        for (y in 1..input.size - 2) {
            for (x in 1..input[y].size - 2) {
                res += getScore(y, x, input)
            }
        }
        return res
    }

    private fun getScore(y: Int, x: Int, i: Array<IntArray>): Int {
        return if (
            i[y][x] < i[y + 1][x]
            && i[y][x] < i[y - 1][x]
            && i[y][x] < i[y][x + 1]
            && i[y][x] < i[y][x - 1]
        ) {
            i[y][x] + 1
        } else {
            0
        }
    }

    fun part2(): Int {
        for (y in 1..input.size - 2) {
            for (x in 1..input[y].size - 2) {
                if (!allTiles.contains(Pair(y, x)) && input[y][x] != 9) {
                    allBasins[Pair(y, x)] = getNextTile(y, x, mutableSetOf()).size
                }
            }
        }
        return allBasins.values.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    private fun getNextTile(y: Int, x: Int, visitedTiles: MutableSet<Pair<Int, Int>>): MutableSet<Pair<Int, Int>> {
        visitedTiles.add(Pair(y, x))
        allTiles.add(Pair(y, x))
        val adjacentTiles = setOf(
            Pair(y, x + 1),
            Pair(y, x - 1),
            Pair(y + 1, x),
            Pair(y - 1, x)
        )
        adjacentTiles
            .filter { !visitedTiles.contains(it) && input[it.first][it.second] != 9 }
            .forEach {
                getNextTile(it.first, it.second, visitedTiles)
            }
        return visitedTiles
    }
}
