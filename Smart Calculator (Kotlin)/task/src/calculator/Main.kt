package calculator

fun main() {
    while (true) {
        val input = readln()
        when {
            input == "/exit" -> break
            input == "/help" -> println("The program evaluates some math expressions")
            input.isEmpty() -> continue
            else -> {
                val parser = Parser(input)
                parser.parse()
                val tokens = parser.tokens
//                println(tokens)
                val solution = Solution(tokens)
                println(solution.solve())
//                println(solution.postfix)
            }
        }
    }
    println("Bye!")
}
