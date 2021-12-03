package optimized2021

import java.io.File

private val input = File("./src/main/kotlin/inputs/input01.txt").readLines().map(String::toInt)

fun main() {
	println(solve(1))
	println(solve(3))
}
fun solve(offset: Int): Int {
	return input.drop(offset).filterIndexed{ index, i -> i > input[index]}.count()
}