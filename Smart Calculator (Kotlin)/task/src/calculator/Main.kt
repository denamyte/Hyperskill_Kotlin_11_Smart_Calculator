package calculator

fun main() {
    while (true) {
        val input = readln()
        when {
            input == "/exit" -> break
            input == "/help" -> println("The program evaluates some math expressions")
            input.startsWith("/") -> println("Unknown command")
            input.isEmpty() -> continue
            else -> {
                try {
                    val parser = Parser(input)
                    parser.parse()
                    val tokens = parser.tokens
//                    println(tokens)
                    val solution = Solution(tokens)
                    println(solution.solve())
//                    println(solution.postfix)
                } catch (e: Exception) {
                    println("Invalid expression")
                }
            }
        }
    }
    println("Bye!")
}
