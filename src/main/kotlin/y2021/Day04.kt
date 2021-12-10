package y2021

import java.io.File

private val input =
	File("./src/main/kotlin/inputs/input04.txt").readLines().filter { it != "" }.map { it.replace("  ", " ") }
private val nums = input[0].split(",").map { it.toInt() }
private val play = mutableListOf<Int>()
private var sqrs = mutableListOf<Pair<Int, Boolean>>()
private val winners = mutableListOf<Int>()

fun main() {
	input.drop(1).forEach {
		it.trim().split(" ")
			.forEach { i ->
				sqrs.add(Pair(i.toInt(), false))
			}
	}

	val p1 = { isWin: Int, _: Int ->
		if (isWin != 0) isWin * play.last()
		else null
	}

	val p2 = { isWin: Int, sqrIndex: Int ->
		if (isWin != 0 && !winners.contains(sqrIndex)) {
			if (sqrs.size / 25 - 1 == winners.size) isWin * play.last()
			else {
				winners.add(sqrIndex)
				null
			}
		} else null
	}
	
	val day = Day04()
	println(day.solve(p1))
	println(day.solve(p2))
}

class Day04 {

	fun solve(rule: (Int, Int) -> Int?): Int {
		nums.forEach { newNum ->
			play.add(newNum)
			for (i in sqrs.indices) {
				if (sqrs[i].first == newNum) sqrs[i] = sqrs[i].copy(first = sqrs[i].first, second = true)
			}
			for (sqrIndex in sqrs.indices step 25) {
				val isWin = isBingo(sqrIndex)
				val res: Int? = rule(isWin, sqrIndex)
				if (res != null) return res
			}
		}
		return 0
	}

	private fun isBingo(i: Int): Int {
		for (ln in 0..4) {
			val x = isBingoLine(i, ln, 5)
			val y = isBingoLine(i, ln * 5, 1)
			if (x != 0) return x
			if (y != 0) return y
		}
		return 0
	}

	private fun isBingoLine(i: Int, start: Int, jump: Int): Int {
		var pos = i + start
		var count = 0
		for (n in 0..4) {
			if (play.contains(sqrs[pos].first)) count++
			pos += jump
		}
		return if (count != 5) {
			0
		} else {
			var score = 0
			for (x in 0..24) {
				if (!sqrs[i + x].second) score += sqrs[i + x].first
			}
			score
		}
	}
}