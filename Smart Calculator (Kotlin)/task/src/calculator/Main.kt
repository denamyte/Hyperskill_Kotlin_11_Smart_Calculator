package calculator

fun main() {
    val vars = Variables()
    while (true) {
        val input = readln()
        when {
            input == "/exit" -> break
            input == "/help" -> println("The program evaluates some math expressions")
            input.startsWith("/") -> println("Unknown command")
            input.isEmpty() -> continue
            else -> {
                try {
                    val parts = Validator.validate(input, vars)
                    if (parts.isError) {
                        println(parts.errorString)
                        continue
                    }

                    val parser = Parser(parts.expression)
                    parser.parse()

                    val solution = Solution(parser.tokens)
                    if (parts.isAssignment) {
                        vars.setVariable(parts.firstStr, solution.solve())
                    } else println(solution.solve())

                } catch (e: Exception) {
                    println("Invalid expression")
                }
            }
        }
    }
    println("Bye!")
}
