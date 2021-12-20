package y2021

import java.io.File

private val raw = File("./src/main/kotlin/inputs/input20.txt").readText().split("===")
private val rules = raw.first().toCharArray()
private val inputRaw = raw[1].trim().split("\n").map { it.trim().toMutableList() }
var image: MutableList<MutableList<Char>> = mutableListOf()


fun main() {
    val day = Day20()
    day.part1(2)
}

class Day20 {
    fun part1(cycles: Int) {
        for (row in 0..cycles) {
            image.add(".".repeat(cycles * 2 + inputRaw.size + 2).toMutableList())
        }
        inputRaw.forEach {
            image.add(
                (".".repeat(cycles + 1).toMutableList() + it + ".".repeat(cycles + 1).toMutableList()).toMutableList()
            )
        }
        for (row in 0..cycles) {
            image.add(".".repeat(cycles * 2 + inputRaw.size + 2).toMutableList())
        }

        println("empty")
        image.forEach {
            it.forEach { print(it) }
            println()
        }
        for (t in 0 until cycles) {
            val newImage: MutableList<MutableList<Char>> = MutableList(image.size){MutableList(image.first().size){'.'} }
            val range = (cycles-t..image.size-3+t)
            println(range)
            println(image.size)
            for (y in cycles-t..image.size-3+t) {
                for (x in cycles-t..image[y].size-3+t) {
                    newImage[y][x] = noiseReduction(x, y)
                }
            }
            image = newImage.toMutableList()
            var count = 0
            println(image.size)
            image.forEach {
                count += it.count { it=='#' }
            }
            println("res: $count")
        }
    }

    fun noiseReduction(x: Int, y: Int): Char {
        var code = ""
        for (height in (y - 1)..(y + 1)) {
            for (width in (x - 1)..(x + 1)) {
                code += when (image[height][width]) {
                    '#' -> 1
                    else -> 0
                }
            }
        }
        return rules[code.toInt(2)]
    }
}