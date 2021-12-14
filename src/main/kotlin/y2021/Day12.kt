package y2021

import java.io.File

private val inputRaw = File("./src/main/kotlin/inputs/input12.txt").readLines().toMutableList()
private var input = mapOf<String, List<String>>()

fun main() {
    for (i in inputRaw.indices) {
        if (inputRaw[i].endsWith("start") || inputRaw[i].startsWith("end")) {
            inputRaw[i] = switchOrder(inputRaw[i])
        }
        if (!(inputRaw[i].startsWith("start") || inputRaw[i].endsWith("end"))) {
            inputRaw.add(switchOrder(inputRaw[i]))
        }
    }
    input = inputRaw
        .map { it.split("-") }
        .groupBy({ it[0] }, { it[1] })

    val day = Day12()
    println(day.part1())
    println(day.part2())
}

fun switchOrder(str: String): String {
    return str.split("-").reversed().joinToString(separator = "-").removePrefix("[").removeSuffix("]")
}


class Day12 {

    fun part1(): Int {
        return findPath(mutableListOf("start")).size
    }

    fun part2(): Int {
        return findPath2(mutableListOf("start")).size
    }

    private fun findPath(traversed: MutableList<String>): MutableSet<MutableList<String>> {
        val possiblePaths: MutableSet<MutableList<String>> = mutableSetOf()
        input[traversed.last()]!!.filterNot { it.isSmallCave() && it in traversed }
            .forEach {
                if (it == "end") {
                    possiblePaths.add((traversed + it).toMutableList())
                } else {
                    val adjacentPointPath = traversed.toMutableList()
                    adjacentPointPath.add(it)
                    findPath(adjacentPointPath).forEach { p ->
                        possiblePaths.add((p.toMutableList()).toMutableList())
                    }
                }
            }
        return possiblePaths
    }

    private fun findPath2(traversed: MutableList<String>): MutableSet<MutableList<String>> {
        val possiblePaths: MutableSet<MutableList<String>> = mutableSetOf()
        input[traversed.last()]!!.filter { it.canBeVisited(traversed) }
            .forEach {
                if (it == "end") {
                    possiblePaths.add((traversed + it).toMutableList())
                } else {
                    val adjacentPointPath = traversed.toMutableList()
                    adjacentPointPath.add(it)
                    findPath2(adjacentPointPath).forEach { p ->
                        possiblePaths.add((p.toMutableList()).toMutableList())
                    }
                }
            }
        return possiblePaths
    }

    private fun String.isSmallCave(): Boolean {
        return this[0].isLowerCase()
    }

    private fun String.canBeVisited(path: MutableList<String>): Boolean {
        if (this.isSmallCave()) {
            val occurrences = path.filter { it.isSmallCave() }.groupingBy { it }.eachCount()
            return if (occurrences.count { (k, v) -> v >= 2 } >= 1) !occurrences.contains(this)
            else true
        }
        return !(this.isSmallCave() && this in path)
    }
}