package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input02.txt").readLines()

fun main() {
	val day = Day02()
	println(day.part1())
	println(day.part2())
}

class Day02 {

	fun part1(): Int {
		val res = arrayOf(0, 0)
		input.forEach {
			val direction = it.split(" ")
			when (direction[0]) {
				"up" -> res[0] -= direction[1].toInt()
				"down" -> res[0] += direction[1].toInt()
				"forward" -> res[1] += direction[1].toInt()
			}
		}
		return res[0] * res[1]
	}

	fun part2(): Int {
		val res = arrayOf(0, 0)
		var aim = 0
		input.forEach {
			val direction = it.split(" ")
			when (direction[0]) {
				"up" -> aim -= direction[1].toInt()
				"down" -> aim += direction[1].toInt()
				"forward" -> {
					res[1] += direction[1].toInt()
					res[0] += direction[1].toInt()*aim
				}
			}
		}
		return res[0] * res[1]
	}
}
