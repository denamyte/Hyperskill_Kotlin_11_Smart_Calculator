fun main() = "#[0-9a-fA-F]{6}\\b".toRegex()
    .findAll(readln())
    .joinToString("\n") { it.value }
    .let(::println)
