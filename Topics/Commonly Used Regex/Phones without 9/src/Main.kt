fun main() =
    Regex("""\(?[0-8]{3}\)?-?[0-8]{3}-?[0-8]{4}""")
        .findAll(readln())
        .map { it.value }.forEach(::println)