package y2021

import java.io.File
import java.lang.Integer.MAX_VALUE
import kotlin.math.abs
import kotlin.math.roundToInt

private val input =  File("./src/main/kotlin/inputs/input07.txt").readText().split(",").map { it.toInt() }.sortedBy { it }.toMutableList()

fun main() {
    val day = Day07()
    day.part1()
    day.solve()
}

class Day07 {
    fun part1(){
        val median = if(input.size%2 == 1){
            input[(input.size)/2]
        }else{
            (input[(input.size)/2] + input[(input.size)/2 -1])/2
        }
        var count = 0
        input.forEach{
            count += abs(it-1)
        }
        println(count)
    }

    fun part2(){
        val average = input.average().roundToInt()
        println(average)
        var count = 0
        println(input.forEach{i -> count += fuel(Math.abs(i-average))})
        input.forEach{i -> println(fuel(Math.abs(i-average)))}
        println(count)
    }

    fun fuel(x: Int): Int{
        return ((x +1)*x)/2
    }

    fun solve() {
        var count = MAX_VALUE
            val temp = input.reduce{acc,i ->
                println(i)
                acc + abs(i-1)
            }
            if(temp < count) count = temp
        println(count) //100728020
    }
}