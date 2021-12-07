package y2021

import java.io.File
import kotlin.math.floor

private var input =
	File("./src/main/kotlin/inputs/input07.txt").readText().split(",").map { it.toInt() }.toMutableList()

fun main(){
	input.forEach{ println(it)}
	println(floor(input.average()))
}

class Day07 {
}