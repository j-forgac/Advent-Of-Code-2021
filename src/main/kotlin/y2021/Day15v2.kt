package y2021

import java.io.File
import kotlin.math.pow
import kotlin.system.measureTimeMillis

private val input = File("./src/main/kotlin/inputs/input15.txt").readLines()
	.map { it.toCharArray().map { i -> Character.getNumericValue(i) } }
private val lineLength = input.first().size
private val totalSize = input.first().size.toDouble().pow(2).toInt() - 1
private val graph: MutableMap<Int, Node> = mutableMapOf()
private val dist: MutableMap<Int,Int> = (0..totalSize).associateWith { Int.MAX_VALUE }.toMutableMap()
private val unVisited: MutableSet<Int> = (0..totalSize).toMutableSet()
private val visit: MutableSet<Int> = mutableSetOf()

fun main() {
	val time = measureTimeMillis {
		println(totalSize)
		val day = Day15v2()
		day.solve()
	}
	println("the time was: $time ms")
}

class Day15v2() {
	fun solve(){
		indexNodes()
		var curNode = 0
		while (unVisited.contains(99)) {
			unVisited.remove(curNode)
			visit.add(curNode)
			graph[curNode]!!.adjacent.forEach {
				val distToNewNode = dist[curNode]!!.plus(graph[it]!!.weight)
				if(distToNewNode < dist[it]!!) dist[it] = distToNewNode
			}
			if(unVisited.contains(99)){
				println(curNode)
				println(unVisited)
				println(visit)
				println(graph[curNode]!!.adjacent.filter { it in unVisited })
				println("ok: ${graph[curNode]!!.adjacent.filter { it in unVisited }.sortedBy { dist[it]}[0]}")
				println("${graph[curNode]!!.adjacent} : ${graph[curNode]!!.weight}")
				println("picks: ${graph[curNode]!!.adjacent.filter { it in unVisited }.minOf {it}}")
				curNode = graph[curNode]!!.adjacent.filter { it in unVisited }.sortedBy { dist[it]}[0]
			}
		}
		println(dist[totalSize])
	}

	fun indexNodes() {
		dist[0] = 0
		for (posY in input.indices) {
			for (posX in input[posY].indices) {
				val index = posY * lineLength + posX
				val adjacent = setOf(
					Pair(posY,posX+1),
					Pair(posY + 1,posX),
					Pair(posY,posX-1),
					Pair(posY - 1,posX)
				).filter { input.getOrNull(it.first)?.getOrNull(it.second) != null }
					.map { it.first * lineLength + it.second }.toSet()

				graph[index] = Node(input[posY][posX], adjacent)
			}
		}
	}
}


class Node(val weight: Int, val adjacent: Set<Int>) {
}

