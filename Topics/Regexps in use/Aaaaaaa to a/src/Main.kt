fun main() = "[aA]+".toRegex()
    .replace(readln(), "a")
    .let(::println)