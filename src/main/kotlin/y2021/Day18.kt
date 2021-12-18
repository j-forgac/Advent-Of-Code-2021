package y2021

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

private val input = File("./src/main/kotlin/inputs/input18.txt").readLines()


fun main() {
	val day = Day18()
	day.getFinal("[[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]],[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]]")
	//day.getFinal("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
}

class Day18() {
	fun part1() {
		var totalSum = ""
		input.forEach {

		}
	}

	fun getFinal(input: String) {
		println(input)
		var prev = ""
		var cur = input
		do {
			prev = cur
			cur = explode(prev)
			if(cur == prev) cur = splitBig(cur)
			println(cur)
			/*do {
				prev = cur
				cur = splitBig(prev)
				cur = explode(cur)
			} while (cur != prev)*/
		} while (cur != prev)
		println(cur)
	}

	fun explode(input: String): String {
		var depth = 0
		input.forEachIndexed { i, ch ->
			when (ch) {
				'[' -> depth++
				']' -> depth--
			}
			if (depth == 5) {
				println()
				println(input)
				val pair = Regex(""".{${i}}(\d+,\d+).*""").find(input)!!.destructured.component1().split(",")
					.map { it.toInt() }
				println(pair)
				val oldStr = input.split(pair.toString().replace(" ", ""), limit = 2)
				var replacePair = ",0"
				var (suffixL, numberL, prefixL) = Regex("""(.*?)(\d+)(.*)""").find(oldStr[0].reversed())?.destructured?.toList()
					?.map { it.reversed() }
					?: listOf(oldStr[0], "", "")
				var (prefixR, numberR, suffixR) = Regex("""(.*?)(\d+)(.*)""").find(oldStr[1])?.destructured?.toList()
					?: listOf(oldStr[1], "", "")
				println("divided: ")
				println("$prefixL <- : -> $numberL <- : -> $suffixL")
				println("$prefixR : $numberR : $suffixR")
				println("divided:")
				if (!"$prefixL$numberL$suffixL".last().isDigit()) replacePair = replacePair.substring(1)

				//increase value of the closest numbers (if there is none, the replacement of pair 0 is the first number -> no comma needed)
				if (numberL.isNotEmpty()) numberL = (numberL.toInt() + pair[0]).toString()

				if (numberR.isNotEmpty()) numberR = (numberR.toInt() + pair[1]).toString()

				println("res: $prefixL$numberL$suffixL$replacePair$prefixR$numberR$suffixR \n")
				return "$prefixL$numberL$suffixL$replacePair$prefixR$numberR$suffixR"
			}
		}
		return input
	}

	fun splitBig(input: String): String {
		if (!Regex(".*?(\\d{2}).*").matches(input)) return input
		println("split: $input")
		val (num) = Regex(".*?(\\d{2}).*").find(input)!!.destructured
		println(
			"splitted: $num --> ${
				input.replaceFirst(
					num,
					"[${floor(num.toFloat() / 2).toInt()},${ceil(num.toFloat() / 2).toInt()}]"
				)
			}"
		)
		return input.replaceFirst(num, "[${floor(num.toFloat() / 2).toInt()},${ceil(num.toFloat() / 2).toInt()}]")
	}
}