package y2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input08.txt").readLines().map { it.split(" | ") }
private val inputP1 = input.map {
    it[1].split(" ")
        .map { i -> i.trim() }
}
private val inputP2 = input.map {
    it.map { s ->
        s.split(" ")
            .map { it.toCharArray().sorted().joinToString("") }
    }
}
fun main() {
    val day = Day08()
    println(day.part1())
    println(day.part2())
}

class Day08 {
    fun part1(): Int {
        val numLengths = listOf(2,4,3,7)
        return inputP1.sumOf { it.count { s -> numLengths.contains(s.length) } }
    }

    fun part2(): Int {
        return inputP2.fold(0){acc, list -> acc + decode(list[0], list[1])}
    }

    //TODO:přepsat pogrom kód

    private fun decode(numList: List<String>, output: List<String>): Int {
        val solved: HashMap<Int, String> = HashMap()
        solved[1] = numList.single { it.length == 2 }
        solved[4] = numList.single { it.length == 4 }
        solved[7] = numList.single { it.length == 3 }
        solved[8] = numList.single { it.length == 7 }
        solved[6] = numList.single { it.length == 6 && (!it.contains(solved[1]!![0]).xor(!it.contains(solved[1]!![1]))) } // 6 is the only number of length 6 that doesn't contain both segments of 1
        val bottomSegments = ((solved[6]!!.toSet().minus(solved[4]!!.toSet())).minus(solved[7]!!.toSet().minus(solved[1]!!.toSet())))// bottom and bottom left segment
        solved[0] = numList.single { !solved.containsValue(it) && it.length == 6 && it.contains(bottomSegments.elementAt(0)) && it.contains(bottomSegments.elementAt(1)) }//except of number 6 is the only number of length 6 that covers both bottom segments
        solved[9] = numList.single { it.length == 6 && !solved.containsValue(it) }// last possible number of length 6
        solved[2] = numList.single { it.length == 5 && it.contains(bottomSegments.elementAt(0)) && it.contains(bottomSegments.elementAt(1)) } //the only number of length 5 overlapping both bottom segments
        solved[3] = numList.single { it.length == 5 && it.contains(solved[1]!![0]) && it.contains(solved[1]!![1]) } // the only number of length 5 covering entire number 1
        solved[5] = numList.single { !solved.containsValue(it) } // last possible number of length five
        return output.fold("") { acc, s -> acc + solved.filterValues { it == s }.keys.first()}.toInt()
    }
}