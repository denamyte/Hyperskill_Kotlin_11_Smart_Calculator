fun main() =
    Regex("""\b(\d\d){1,2}(\.\d\d){2}\b""")
        .findAll(readln())
        .map { it.value }
        .forEach(::println)