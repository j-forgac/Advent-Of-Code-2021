package y2021

import java.io.File
import kotlin.system.measureTimeMillis

private val rawData = File("./src/main/kotlin/inputs/input19.txt").readText().split("--- scanner ").drop(1)
private val scMap: MutableMap<Int, Scanner> = mutableMapOf()
private val allBeacons: MutableList<List<Int>> = mutableListOf()

fun main() {
	val timeInMillis = measureTimeMillis {
		val day = Day19()
		day.part1()
	}
	println("time: $timeInMillis ms")
}

class Day19 {


	val rotations: MutableMap<Int, MutableList<Int>> = mutableMapOf()

	init {
		rawData.forEach {
			val raw = it.split(" ---")
			scMap[raw[0].toInt()] = Scanner(raw[1].trim())
		}

	}

	fun part1() {
		val sc0 = scMap[0]
		val sc1 = scMap[1]
		sc0!!.allDists.forEach { bcn0 ->
			for (x in 0..23) {
				sc1!!.allDists.forEach { bcn1 ->
					val rotated = bcn1.value.map { getRotation(it[0],it[1],it[2],x) }.toMutableList()
					val overlap = rotated.intersect(bcn0.value).toMutableList()
					if(overlap.size>=11) {
						overlap.add(bcn0.key)
						println(allBeacons.contains(bcn0.key))
						overlap.filter { it !in allBeacons }.forEach { allBeacons.add(it) }
						allBeacons.forEach { println(it) }
						println(allBeacons.size)
						println("=== \n")
					}
				}
			}
		}
	}

	private fun List<Int>.getAbsolutePosition(center: List<Int>): List<Int>{
		return listOf(center[0]-this[0], center[1]-this[1], center[2]-this[2])
	}

	fun getRotation(X: Int, Y: Int, Z: Int, type: Int): List<Int> {
		return when(type) {
			0 -> listOf(X, Y, Z)
			1 -> listOf(X, -Y, -Z)
			2 -> listOf(-X, Y, -Z)
			3 -> listOf(-X, -Y, Z)
			4 -> listOf(X, Z, -Y)
			5 -> listOf(X, -Z, Y)
			6 -> listOf(-X, Z, Y)
			7 -> listOf(-X, -Z, -Y)
			8 -> listOf(Y, Z, X)
			9 -> listOf(Y, -Z, -X)
			10 -> listOf(-Y, Z, -X)
			11 -> listOf(-Y, -Z, X)
			12 -> listOf(Y, X, -Z)
			13 -> listOf(Y, -X, Z)
			14 -> listOf(-Y, X, Z)
			15 -> listOf(-Y, -X, -Z)
			16 -> listOf(Z, X, Y)
			17 -> listOf(Z, -X, -Y)
			18 -> listOf(-Z, X, -Y)
			19 -> listOf(-Z, -X, Y)
			20 -> listOf(Z, Y, -X)
			21 -> listOf(Z, -Y, X)
			23 -> listOf(-Z, Y, X)
			else -> listOf(-Z, -Y, -X)
		}
	}
}

class Scanner(input: String) {
	val allPoints: MutableSet<List<Int>> = mutableSetOf()
	val allDists: MutableMap<List<Int>,Set<List<Int>>> = mutableMapOf()

	init {
		input.split("\n").forEach {
			allPoints.add(it.trim().split(",").map { it.toInt() }.toList())
		}
		allPoints.forEach { p1 ->
			val pointDists: MutableSet<List<Int>> = mutableSetOf()
			allPoints.forEach { p2 ->
				if (p1 != p2) {
					pointDists.add(listOf(p1[0] - p2[0], p1[1] - p2[1], p1[2] - p2[2]))
				}
			}
			allDists[p1] = pointDists
		}
	}
}