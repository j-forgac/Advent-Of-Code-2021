package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input05.txt").readLines()
private val points = mutableMapOf<Pair<Int, Int>, Int>()

fun main() {
	val day = Day05()
	println(day.part1())
}

class Day05 {

	fun part1() {
		generatePoints()
		println("result:")
		println(points.count { (_, value) -> value > 0 })
	}

	fun generatePoints() {
		var count = 0
		input.forEach {
			val line = it.split(" -> ")
			val point1 = line[0].split(",").map { i -> i.toInt() }
			val point2 = line[1].split(",").map { i -> i.toInt() }
			var itr1 = 0
			var itr2 = 0
			if (point1[0] == point2[0]) {
				if (point1[1] < point2[1]) {
					itr1 = point1[1]
					itr2 = point2[1]
				} else {
					itr1 = point2[1]
					itr2 = point1[1]
				}
				for (i in itr1..itr2) {
					points[Pair(point1[0], i)] = points[Pair(point1[0], i)]?.plus(1) ?: 0
				}
			} else if (point1[1] == point2[1]) {
				if (point1[0] < point2[0]) {
					itr1 = point1[0]
					itr2 = point2[0]
				} else {
					itr1 = point2[0]
					itr2 = point1[0]
				}
				for (i in itr1..itr2) {
					points[Pair(i, point1[1])] = points[Pair(i, point1[1])]?.plus(1) ?: 0
				}
			}
		}
	}
}