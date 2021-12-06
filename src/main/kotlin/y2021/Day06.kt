package y2021

import java.io.File

private var input =
    File("./src/main/kotlin/inputs/input06.txt").readText().toString().split(",").map { it.toInt() }.toMutableList()
private var colony: MutableMap<Int, Long> = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
private var newColony: MutableMap<Int, Long> = mutableMapOf()

fun main() {
    val day = Day06()
    println(day.part1(80))
    println(day.part2(256))
}

class Day06 {
    fun part1(cycles: Int): Int {
        for (i in 0 until cycles) {
            for (e in input.indices) {
                if (input[e] == 0) {
                    input[e] = 7
                    input.add(8)
                }
                input[e] = input[e] - 1
            }
        }
        return input.size
    }

    fun part2(cycles: Int): Long {
        for (i in 0 until cycles) {
            colony.forEach { (key, value) ->
                when (key) {
                    0 -> {
                        newColony[6] = value
                        newColony[8] = value
                    }
                    7 -> newColony[6] = newColony[6]?.plus(value) ?: value
                    else -> newColony[key - 1] = value
                }
            }
            colony = HashMap(newColony)
            newColony.clear()
        }
        return colony.values.sum()
    }
}