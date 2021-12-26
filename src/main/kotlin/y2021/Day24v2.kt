package y2021

import java.io.File
import kotlin.math.truncate

private val instructions =
	File("./src/main/kotlin/inputs/input24.txt").readLines().map { it.split(" ") }.chunked(18)
private val maxZ: MutableList<Int> = mutableListOf(0)
fun main() {
	val day = Day24v2()
//	day.part1()
	day.part2()
}

class Day24v2() {
	fun part1() {
		simulate("", 0)
	}

	fun simulate(num: String, z: Int): Boolean {
		if(num.length == 14){
			if (z == 0) {
				println(z)
				println(num)
				return true
			}
			println(num.length)
			return false
		}
		val it = instructions[num.length]
		if (it[4][2] == "1") {
//				println("26z + w + ${it[15][2]}")
			for (w in 9 downTo 1) {
				if (simulate("$num$w", z * 26 + w + it[15][2].toInt())) {
					println("z: $z, num: $num, equation: z*26 + w:$w + ${it[15][2].toInt()}");return true;
				}
			}
		} else {
//				println("z%26 ${it[5][2]} == w")
//				println("[z/26] || [z/26] + w + ${it[15][2]}")99919769979468
//																99919765949498
			for (w in 9 downTo 1) {
				if (z % 26 + it[5][2].toInt() == w) {
					if (simulate("$num$w", z / 26)) {
						println("z: $z, num: $num, equation: z/26");return true
					}
				}
			}
			for (w in 9 downTo 1) {
				if (simulate("$num$w", 26 * (z / 26) + w + it[15][2].toInt())) {
					println(println("z: $z, num: $num,  equation: 26*[z/26] + w:$w + ${it[15][2].toInt()}"));return true;
				}
			}
		}
		println(num)
		return false
	}


	fun part2() {
		instructions.forEachIndexed loop@{ index, ins ->
			val prevZ = maxZ.elementAtOrElse(index){0}
			when(ins[4][2]){
				"1" -> {
					println(prevZ*26+9+ins[15][2].toInt())
					maxZ.add(prevZ*26+9+ins[15][2].toInt())
				}
				else -> {
					for(w in 9 downTo 1){
						if(prevZ % 26 + ins[5][2].toInt() == w){
							println(prevZ/26)
							maxZ.add(prevZ/26)
							return@loop
						}
					}
					println(26 * (prevZ / 26) + 9 + ins[15][2].toInt())
					maxZ.add(26 * (prevZ / 26) + 9 + ins[15][2].toInt())
				}
			}
		}
		println()
		maxZ.forEach { println(it) }
		println(maxZ.size)
		println("ee")
		simulate2("",0)
	}

	fun simulate2(num: String, z: Int): Boolean {
		if(num.length == 14){
			if (z == 0) {
				println(z)
				println(num)
				return true
			}else {
				return false
			}
		}
		val it = instructions[num.length]

		if(z > maxZ.elementAt(num.length)){
			println(z)
			println(num)
			println(num.length)
			println(maxZ.elementAt(num.length))
			println()
			return false
		}
		if (it[4][2] == "1") {
			for (w in 1..9) {
				if (simulate2("$num$w", z * 26 + w + it[15][2].toInt())) {
					return true
				}
			}
		} else {
			for (w in 1..9) {
				if (z % 26 + it[5][2].toInt() == w) {
					if (simulate2("$num$w", z / 26)) {
						return true
					}
				}
			}
			for (w in 1..9) {
				if (simulate2("$num$w", 26 * (z / 26) + w + it[15][2].toInt())) {
					return true;
				}
			}
		}
		return false
	}
}
