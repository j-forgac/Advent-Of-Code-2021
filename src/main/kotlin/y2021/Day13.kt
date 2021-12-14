package y2021

import java.io.File


private val rawInput = File("./src/main/kotlin/inputs/input13.txt").readText().split("---").map{it.split("\n").filter { it != "" }}
private var input: MutableSet<MutableList<Int>> = mutableSetOf()
private val fold = rawInput[1].map { it.removePrefix("fold along ").split("=") }

fun main() {
    rawInput.forEach { println(it) }
    fold.forEach { println(it) }
/*
    input = rawInput[0].map { it.split(",").map { it.toInt() }.toMutableList() }
        .toMutableSet()
    rawInput.forEach {
        println("lol")
        it.forEach{ println(it)}
    }
    val day = Day13()
    day.solve()*/
}

class Day13 {
    fun solve() {
        var mapOld = input.toMutableSet()
        fold.forEach { foldRule ->
            val mapNew: MutableSet<MutableList<Int>> = mutableSetOf()
            mapOld.forEach { coo ->
                if (foldRule[0] == "x" && coo[0] > foldRule[1].toInt()) {
                    val pos = foldRule[1].toInt() - coo[0]
                    mapNew.add(mutableListOf(coo[0] + pos * 2, coo[1]))
                } else if (foldRule[0] == "y" && coo[1] > foldRule[1].toInt()) {
                    val pos = foldRule[1].toInt() - coo[1]
                    mapNew.add(mutableListOf(coo[0], coo[1] + pos * 2))
                } else {
                    mapNew.add(coo)
                }
            }
            mapOld = mapNew.toMutableSet()
            println(mapOld.size)
        }
        printPaper(mapOld)
    }

    private fun printPaper(mapInput: MutableSet<MutableList<Int>>) {
        val myCodePrint = mapInput.groupBy({ it[1] }) { it[0] }.toSortedMap()
        val rightMostPoint = mapInput.groupBy({ it[0] }) { it[1] }.toSortedMap().lastKey()
        myCodePrint.forEach { (k, line) ->
            for (i in 0..rightMostPoint) {
                if (line.contains(i)) print("#")
                else print(".")
            }
            println()
        }
    }
}