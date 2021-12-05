package y2021

import java.io.File
import kotlin.math.pow

private val input = File("./src/main/kotlin/inputs/input05.txt").readLines().map {
	it.split(" -> ").map { s ->
		s.split(",")
			.map { i -> i.toInt() }
	}.sortedBy { e -> e[1] }
}
private val points = mutableMapOf<Pair<Int, Int>, Int>()

fun main() {
	val day = Day05v2()
	println(day.part1())
	println(day.part2())
}

class Day05v2 {

	fun part1(): Int {
		points.clear()
		generatePoints()
		return points.count { (_, value) -> value > 0 }
	}

	fun part2(): Int {
		points.clear()
		generatePoints()
		generateDiagonal()
		return points.count { (_, value) -> value > 0 }
	}

	fun generatePoints() {
		input.forEach {
			var point1 = it[0]
			var point2 = it[1]
			if (point1[0] == point2[0]) {
				for (i in point1[1]..point2[1]) {
					points[Pair(point1[0], i)] = points[Pair(point1[0], i)]?.plus(1) ?: 0
				}
			} else if (point1[1] == point2[1]) {
				val temp = it.sortedBy { e -> e[0] }
				point1 = temp[0]
				point2 = temp[1]
				for (i in point1[0]..point2[0]) {
					points[Pair(i, point1[1])] = points[Pair(i, point1[1])]?.plus(1) ?: 0
				}
			}
		}
	}

	fun generateDiagonal() {
		input.forEach {
			val point1 = it[0]
			val point2 = it[1]
			if (point1[0] != point2[0] && point1[1] != point2[1]) {
				if (point1[0] < point2[0]) {
					for (i in point1[1]..point2[1]) {
						points[Pair(point1[0] - point1[1] + i, i)] = points[Pair(point1[0] - point1[1] + i, i)]?.plus(1) ?: 0
					}
				} else if (point1[0] > point2[0]) {
					for (i in point2[0]..point1[0]) {
						points[Pair(i, point1[1] + point1[0] - i)] = points[Pair(i, point1[1] + point1[0] - i)]?.plus(1) ?: 0
					}
				}
			}
		}
	}
}