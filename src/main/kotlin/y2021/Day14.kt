package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input14.txt").readText().split("===")
private var polymer = input[0].trim()
private val rules = input[1].trim().split("\n").map { it.split(" -> ")[0] to it.split(" -> ")[1].first() }.toMap()
private var mapPoly: MutableMap<String, Long> = polymer.parseToWindowed().groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap().toMutableMap()


fun main() {
    val day = Day14()
    day.part1(10)
    day.part2(40)
}

class Day14 {
    fun part1(cycles: Int): Int {
        for (i in 0 until cycles) {
            val newPolymer = polymer.parseToWindowed()
            polymer = ""
            newPolymer.forEach {
                rules.forEach { (k, v) ->
                    if (k in it) {
                        polymer += it.addCharAtIndex(v, 1).take(2)
                    }
                }
            }
            polymer += newPolymer.last()[0]
        }
        val frequency = polymer.groupingBy { it }.eachCount().toList().sortedBy { (_, v) -> v }.toMap()
        return frequency.maxOf { it.value } - frequency.minOf { it.value }
    }


    fun part2(cycles: Int): Long {
        for (i in 0 until cycles) {
            val tempMap = mutableMapOf<String, Long>()
            rules.forEach { (ruleK, ruleV) ->
                if (mapPoly[ruleK] != null) {
                    val newPolymer = ruleK.parseToWindowed().map { it.addCharAtIndex(ruleV, 1) }.single().parseToWindowed()
                    newPolymer.forEach {
                        tempMap[it] = tempMap[it]?.plus(mapPoly[ruleK]!!) ?: mapPoly[ruleK]!!
                    }
                }
            }
            mapPoly = tempMap.toMutableMap()
        }
        return mapPoly.getResult()
    }

    private fun MutableMap<String,Long>.getResult(): Long{
        val res = mutableMapOf<Char,Long>()
        this.forEach { (k, v) ->
            res[k[1]] = res[k[1]]?.plus(v)?:v
        }
        res[polymer[0]] = res[polymer[0]]?.plus(1L)?:1L
        return res.maxOf { it.value } - res.minOf { it.value }
    }

    private fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply { insert(index, char) }.toString()
}

private fun String.parseToWindowed() =
    this.chunked(1).windowed(2).map { it.joinToString().replace(", ", "") }