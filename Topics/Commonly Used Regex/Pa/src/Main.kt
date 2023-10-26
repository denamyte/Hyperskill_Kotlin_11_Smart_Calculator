fun main() =
    Regex("""pa[a-z]+\b""")
        .findAll(readln())
        .map { it.value }
        .forEach(::println)
