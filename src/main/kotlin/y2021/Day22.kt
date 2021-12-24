package y2021

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

private val input = File("./src/main/kotlin/inputs/input22.txt").readLines()
	.map {
		Cube(it.replace(",", "").split(Regex("""[a-z]="""))
			.map { str -> str.split("..") })
	}

fun main() {
	val time = measureTimeMillis {
		val day = Day22()
		println("p1: ${day.part1()}")
		println("p2: ${day.part3()}")
	}
	println("time: $time ms")
}

class Day22 {
	fun part1(): Int {
		val coos: MutableSet<Coordinate> = mutableSetOf()
		input.take(20).forEach {
			for (x in it.rX) {
				for (y in it.rY) {
					for (z in it.rZ) {
						if (it.state == "on") {
							coos.add(Coordinate(x, y, z))
						} else {
							coos.remove(Coordinate(x, y, z))
						}
					}
				}
			}
		}
		return coos.size
	}


	fun part3(): Long {
		val on: MutableList<Cube> = mutableListOf(input[0])
		val off: MutableList<Cube> = mutableListOf()
		input.drop(1).forEach { inputCube ->
			val tempOn: MutableList<Cube> = mutableListOf()
			val tempOff: MutableList<Cube> = mutableListOf()
			for (cube in on) {
				val intersection = inputCube.getIntersection(cube)
				if (intersection != null) tempOff.add(intersection)
			}
			for (i in 0 until off.size) {
				val intersection = inputCube.getIntersection(off[i])
				if (intersection != null) tempOn.add(intersection)
			}
			if (inputCube.state == "on") on.add(inputCube)
			on.addAll(tempOn)
			off.addAll(tempOff)
		}
		return on.fold(0L) { acc, cube -> acc + cube.size } - off.fold(0L) { acc, cube -> acc + cube.size }
	}
}

data class Coordinate(var x: Int, var y: Int, var z: Int)

data class Cube(
	var state: String,
	var rX: IntRange,
	var rY: IntRange,
	var rZ: IntRange,
	var size: Long = 0L,
) {
	constructor(inputData: List<List<String>>) : this(
		inputData[0].single().trim(),
		IntRange(
			inputData[1][0].toInt(),
			inputData[1][1].toInt()
		),
		IntRange(
			inputData[2][0].toInt(),
			inputData[2][1].toInt()
		),
		IntRange(
			inputData[3][0].toInt(),
			inputData[3][1].toInt()
		),
	)

	init {
		size = getVolume();
	}

	fun getVolume(): Long {
		return ((abs(rX.first - rX.last) + 1).toLong() * (abs(rY.first - rY.last) + 1).toLong() * (abs(rZ.first - rZ.last) + 1).toLong())
	}

	fun getIntersection(other: Cube): Cube? {
		return if (rX.doesOverlap(other.rX) && rY.doesOverlap(other.rY) && rY.doesOverlap(other.rY) && rZ.doesOverlap(other.rZ)) {
			Cube("", rX.common(other.rX), rY.common(other.rY), rZ.common(other.rZ))
		} else {
			null
		}
	}

	fun IntRange.common(other: IntRange): IntRange {
		return IntRange(max(first, other.first), min(last, other.last))
	}

	fun IntRange.doesOverlap(other: IntRange): Boolean {
		return max(first, other.first) <= min(last, other.last)
	}
}