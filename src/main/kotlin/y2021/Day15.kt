package y2021

import java.io.File
import kotlin.system.measureTimeMillis

private val input = File("./src/main/kotlin/inputs/input15.txt").readLines()
    .map { it.toCharArray().map { i -> Character.getNumericValue(i) } }
private val allPaths: MutableMap<Int, MutableList<Pair<Int, Int>>> = mutableMapOf()
private var lol = 0L

fun main() {
    val day = Day15()
    day.part1()
}

class Day15() {
    fun part1() {
        val time = measureTimeMillis {
            var estimate = 0
            input.forEach {
                estimate += it[0]
            }
            input.last().drop(1).forEach { estimate += it }
            allPaths[estimate] = mutableListOf()
            getNextTile(0, 0, mutableListOf())
            println(allPaths.keys)
        }
        println(time)
        println(lol)
    }

    private fun getNextTile(y: Int, x: Int, oldPath: MutableList<Pair<Int, Int>>) {
        lol++
        val path = oldPath.toMutableList()
        path.add(Pair(y, x))
        if (allPaths.any { (k, v) -> k < path.getScore() }) return
        if (path.last().first == input.size - 1 && path.last().second == input.last().size - 1) {
            allPaths[path.getScore()] = path
            return
        }
        var adjacentTiles = setOf(
            Pair(y, x + 1),
            Pair(y + 1, x),
            Pair(y, x - 1),
            Pair(y - 1, x)
        )
        adjacentTiles = adjacentTiles.asSequence().filter { !path.contains(it) && it.isInBoundsOf() }.sortedBy { input[it.first][it.second] }.toMutableSet()
        adjacentTiles.filter { !path.contains(it) && it.isInBoundsOf() }.forEach {
            getNextTile(it.first, it.second, path)
        }
    }

    fun Pair<Int, Int>.isInBoundsOf(): Boolean {
        return input.getOrNull(this.first)?.getOrNull(this.second) != null
    }

    fun MutableList<Pair<Int, Int>>.getScore(): Int {
        var cost = 0
        this.forEach {
            cost += input[it.first][it.second]
        }
        return cost
    }
}