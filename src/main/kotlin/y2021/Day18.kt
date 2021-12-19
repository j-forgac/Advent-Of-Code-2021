package y2021

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.system.measureTimeMillis

private val input = File("./src/main/kotlin/inputs/input18.txt").readLines()


fun main() {
	val day = Day18()
	val timeInMillis = measureTimeMillis {
		day.part1()

	day.part2()
	}

	println("the time was: $timeInMillis ms")
}

class Day18 {
	fun part1() {
		var totalSum = getFinal("[${input[0]},${input[1]}]")
		input.drop(2).forEach {
//			println(totalSum)
//			println("+ $it")
			totalSum = getFinal("[$totalSum,$it]")
//			println("= $totalSum\n")
		}
		println(magnitude(totalSum))
	}

	fun part2() {
		var highestMagnitude = 0
		input.forEach { first ->
			input.forEach { second ->
				if (first != second) {
					val temp = magnitude(getFinal("[$first,$second]"))
//					println("[$first,$second] \n = $temp")
					if (temp > highestMagnitude) highestMagnitude = temp
				}
			}
		}
		println(highestMagnitude)
	}

	fun getFinal(input: String): String {
		var prev = ""
		var cur = input
		var i = 0
		do {
			prev = cur
			cur = explode(prev)
			if (cur == prev) {
				cur = splitBig(cur)
			}
			i++
		} while (cur != prev)
		return cur
	}

	fun explode(input: String): String {
		var depth = 0
		input.forEachIndexed { i, ch ->
			when (ch) {
				'[' -> depth++
				']' -> depth--
			}
			if (depth == 5) {
				val pair = Regex(""".{${i}}(\d+,\d+).*""").find(input)!!.destructured.component1().split(",")
					.map { it.toInt() }
				val oldStr = listOf(input.substring(0, i), input.substring(i + pair.toString().length - 1))
				var replacePair = ",0"
				var (suffixL, numberL, prefixL) = Regex("""(.*?)(\d+)(.*)""").find(oldStr[0].reversed())?.destructured?.toList()
					?.map { it.reversed() }
					?: listOf("", "", oldStr[0])
				var (prefixR, numberR, suffixR) = Regex("""(.*?)(\d+)(.*)""").find(oldStr[1])?.destructured?.toList()
					?: listOf(oldStr[1], "", "")
				if (!"$prefixL$numberL$suffixL".last().isDigit()) replacePair = replacePair.substring(1)

				//increase value of the closest numbers (if there is none, the replacement of pair (0) is the first number -> no comma needed)
				if (numberL.isNotEmpty()) numberL = (numberL.toInt() + pair[0]).toString()

				if (numberR.isNotEmpty()) numberR = (numberR.toInt() + pair[1]).toString()

				return "$prefixL$numberL$suffixL$replacePair$prefixR$numberR$suffixR"
			}
		}
		return input
	}

	fun splitBig(input: String): String {
		if (!Regex(".*?(\\d{2}).*").matches(input)) return input
		val (num) = Regex(".*?(\\d{2}).*").find(input)!!.destructured
		return input.replaceFirst(num, "[${floor(num.toFloat() / 2).toInt()},${ceil(num.toFloat() / 2).toInt()}]")
	}

	fun magnitude(input: String): Int {
		var res = input
		while (Regex(""".*?\d+,\d+.*""").matches(res)) {
			val pair = Regex("""(\d+,\d+)""").find(res)!!.destructured.component1().split(",")
				.map { it.toInt() }
			val result = pair[0] * 3 + pair[1] * 2
			res = res.replaceFirst(pair.toString().replace(" ", ""), result.toString())
		}
		return res.removePrefix("[").removeSuffix("]").toInt()
	}
}