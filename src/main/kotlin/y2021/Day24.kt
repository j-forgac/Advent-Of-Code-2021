package y2021

import java.io.File

private val debugInput = File("./src/main/kotlin/inputs/input24.txt").readLines().map { it.padEnd(9, ' ') }.chunked(18)
private val instructions =
	File("./src/main/kotlin/inputs/input24.txt").readLines().map { it.split(" ") }.chunked(18)

fun main() {
	val day = Day24()
//	instructions.forEach { it.forEach { println(it) };println("\n next: \n") }
	day.part1()
}

class Day24 {
	//chunky začínají inputem pro E
	//následně je Z vynulováno
	//Y vždy vynulováno před tím než se s ním pracuje v daném chunku
	//jediná proměná zachována mezi chunky je Z
	fun part1() {
//		isValid("99919769979468")
		isValid("99919765949498")
//		val desiredZ = 0
//		for(x in 1..9){
//			println(-x-2)
//		}
//		println(15%-4)
//		for(i in 11_111_111_111_111..99_999_999_999_999){
//			val test = i.toString()
//			if(isValid(test)){
//				println(test)
//				break
//			}
//		}
	}

	fun isValid(num: String): MutableMap<Char, Int> {
		println(num.substring(0,14))
		var z = 0
		val aluMap = mutableMapOf(
			'w' to 0,
			'x' to 0,
			'y' to 0,
			'z' to 0
		)
		instructions.forEachIndexed { index, instr ->
			instr.forEach {
				when (it[0]) {
					"add" -> aluMap[it[1].single()] = aluMap[it[1].single()]!!.plus(getValue(aluMap, it[2]))
					"mul" -> aluMap[it[1].single()] = aluMap[it[1].single()]!!.times(getValue(aluMap, it[2]))
					"div" -> aluMap[it[1].single()] = aluMap[it[1].single()]!!.div(getValue(aluMap, it[2]))
					"mod" -> aluMap[it[1].single()] = aluMap[it[1].single()]!!.mod(getValue(aluMap, it[2]))
					"eql" -> {
						aluMap[it[1].single()] = if (aluMap[it[1].single()] == getValue(aluMap, it[2])) 1
						else 0
					}
					else -> aluMap['w'] = num[index].digitToInt()
				}
			}
			println(aluMap)
			println()

		}
		return aluMap
	}

	fun simulate(num: String, z: Int, w: Int): Int {
		println("w $w, s $z")
		val it = instructions[num.length-1]
		println("divided by: ${it[4][2]} ${it[4][2] == "1"}")
		if (it[4][2] == "1") {
			println("inc: ${it[15][2].toInt()}")
			return z * 26 + w + it[15][2].toInt()
		} else {
			if (z % 26 + it[5][2].toInt() == w) {
				return z / 26
			} else {
				return (26 * z / 26) + w + it[15][2].toInt()
			}
		}
	}

	fun getValue(aluMap: MutableMap<Char, Int>, str: String): Int {
		return if (str.toIntOrNull() != null) str.toInt()
		else aluMap[str.single()]!!
	}
}