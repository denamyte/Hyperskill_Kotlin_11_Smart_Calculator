fun main() {
    Regex("""([A-Z][a-z]+)( [A-Z][a-z]+)?""")
        .findAll(readln())
        .map { it.value }
        .forEach(::println)

}