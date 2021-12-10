package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input10.txt").readLines()
private val scoreChart1 = mutableMapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
private val scoreChart2 = mutableMapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
private val failValues = mutableMapOf<String, Long>()
private val groups = listOf("()", "[]", "{}", "<>")
private val invalid = mutableListOf<String>()


fun main() {
    val opening = listOf('(','{','<','[')
    val closing = listOf(')','}','>',']')
    opening.forEachIndexed { i1, o ->
        closing.forEachIndexed { i2, s ->
            if (i1 != i2) invalid.add("$o$s")
        }
    }
    val day = Day10()
    println(day.part1())
    println(day.part2())
}

class Day10 {
    fun part1(): Int {
        return input.fold(0){acc, str -> acc + findErr1(str)}
    }

    fun part2(): Long {
        input.forEach {
            val fail = findErr2(it)
            if (fail != 0L) failValues[it] = fail
        }
        return failValues.values.sortedDescending()[(failValues.size-1)/2]
    }

    //finding errors is almost identical in both parts, since the same logic still applies

    private fun findErr1(input: String): Int {
        var text = input
        var prevLength = 0
        while (prevLength != text.length) {
            prevLength = text.length
            groups.forEach {
                text = text.replace(it, "")
                invalid.forEach { str ->
                    if (text.contains(str)) return scoreChart1[str[1]]!!
                }
            }
        }
        return 0
    }

    private fun findErr2(input: String): Long {
        var text = input
        var prevLength = 0
        while (prevLength != text.length) {
            prevLength = text.length
            groups.forEach {
                text = text.replace(it, "")
                invalid.forEach { str ->
                    if (text.contains(str)) {
                        return 0
                    }
                }
            }
        }
        return text.reversed().fold(0L) { acc: Long, ch -> acc*5 + scoreChart2[ch]!! }
    }
}