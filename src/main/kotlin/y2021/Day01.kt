package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input01.txt").readLines().map(String::toInt)

fun main() {
	val day = Day01()
	println(day.part1())
	println(day.part2())
}

class Day01 {

	fun part1(): Int {
		println()
		var sum = 0
		for(i in 1 until input.size){
			if(input[i] > input[i-1]) sum++
		}
		return sum
	}

	fun part2(): Int {
		var sum = 0
		for(i in 3 until input.size){
			if(input[i] > input[i-3]) sum++
		}
		return sum
	}
}