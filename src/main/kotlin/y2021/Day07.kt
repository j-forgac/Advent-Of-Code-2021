package y2021

import java.io.File
import java.lang.Integer.MAX_VALUE
import kotlin.math.abs

private val input = File("./src/main/kotlin/inputs/input07.txt").readText().split(",").map { it.toInt() }.sortedBy { it }.toMutableList()

fun main() {

    val day = Day07()
    println(day.part1())
    println(day.part2())
}

fun fuel(x: Int): Int {
    return ((x + 1) * x) / 2
}

class Day07 {

    fun part1(): Int {
        var res = MAX_VALUE
        input.forEach{ item ->
            val temp = input.fold(0) { acc, i -> acc + abs(item - i) }
            if(temp < res) res = temp
        }
        return res
    }

    fun part2(): Int {
        var res = MAX_VALUE
        input.forEach{ item ->
            val temp = input.fold(0) { acc, i -> acc + fuel(abs(item - i)) }
            if(temp < res) res = temp
        }
        return res
    }
}