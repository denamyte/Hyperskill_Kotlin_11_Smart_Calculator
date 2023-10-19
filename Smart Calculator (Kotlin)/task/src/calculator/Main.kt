package calculator

fun main() {
    while (true) {
        val input = readln()
        when {
            input == "/exit" -> break
            input.isEmpty() -> continue
            else -> println(input.split(" ").map(String::toInt).sum())
        }
    }
    println("Bye!")
}
