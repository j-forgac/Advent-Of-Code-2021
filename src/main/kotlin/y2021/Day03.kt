package y2021

import java.io.File
import kotlin.math.pow

private val input = File("./src/main/kotlin/inputs/input03.txt").readLines()

fun main() {
	val day = Day03()
	println(day.part1())
	println(day.solve('0','1')*day.solve('1','0'))
}

class Day03 {

	fun part1(): Int {
		val ln = input[0].length
		val commonality0 = IntArray(ln) { 0 }
		val commonality1 = IntArray(ln) { 0 }
		input.forEach {
			for (i in it.indices) {
				if (it[i] == '0') commonality0[i]++
				else commonality1[i]++
			}
		}
		var mostC = 0
		for (i in commonality0.indices) {
			if (commonality1[ln - i - 1] > commonality0[ln - i - 1]) {
				mostC += 2.0.pow(i.toDouble()).toInt()
			}
		}
		val leastC = "1".repeat(ln).toInt(2)
		return (leastC - mostC) * mostC
	}

	fun solve(ch1: Char, ch2: Char): Int{
		val ln = input[0].length
		val commonality0 = IntArray(ln) { 0 }
		val commonality1 = IntArray(ln) { 0 }
		var codes = input.toMutableList()
		for (i in 0 until ln) {
			codes.forEach { it ->
				if (it[i] == '0') commonality0[i]++
				else commonality1[i]++
			}
			codes = if (commonality0[i] <= commonality1[i]) {
				codes.filter { it[i] == ch1 } as MutableList<String>
			} else {
				codes.filter { it[i] == ch2 } as MutableList<String>
			}
			if(codes.size == 1)break
		}
		return codes[0].toInt(2)
	}
}