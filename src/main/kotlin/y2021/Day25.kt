package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input25.txt").readLines().map { it.toCharArray().toMutableList() }

fun main() {
	val day = Day25()
	day.part1()
}

class Day25 {
	private var curState = input.map { it.toMutableList() }.toMutableList()
	private var new = curState.map { it.toMutableList() }.toMutableList()

	fun part1() {
		val width = input.first().size
		val height = input.size
		var counter = 0


		do {
			curState.copy(new)
			curState.forEachIndexed { y, row ->
				row.forEachIndexed { x, fish ->
					if (fish == '>' && curState[y][(x + 1) % width] == '.') {
						new[y][x] = '.'
						new[y][(x + 1) % width] = '>'
					}
				}
			}
			val tmp = new.map { it.toMutableList() }.toMutableList()
			new.forEachIndexed { y, row ->
				row.forEachIndexed { x, fish ->
					if (fish == 'v' && new[(y + 1) % height][x] == '.') {
						tmp[y][x] = '.'
						tmp[(y + 1) % height][x] = 'v'
					}
				}
			}
			new.copy(tmp)
			counter++
			println("turn: $counter")
//			curState.forEach {
//				it.forEach { print(it) }
//				println()
//			}
//			println(curState == new)
//			println()
		} while (curState != new)
		println(counter)
	}

//	fun MutableList<MutableList<Char>>.copy(other: MutableList<MutableList<Char>>) {
//		this = mutableListOf('1')
//	}

	fun MutableList<MutableList<Char>>.copy(other: MutableList<MutableList<Char>>) {
		for(i in 0 until this.size){
			this[i] = other[i].toMutableList()
		}
	}
}