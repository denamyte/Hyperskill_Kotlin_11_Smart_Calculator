package calculator

fun main() {
    while (true) {
        val input = readln()
        when {
            input == "/exit" -> break
            input == "/help" -> println("The program calculates the sum of numbers")
            input.isEmpty() -> continue
            else -> println(input.split(" ").map(String::toInt).sum())
        }
    }
    println("Bye!")
}
