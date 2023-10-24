fun main() {
    val s = readln()
    val (s1, s2) = s.chunked(s.length / 2)
    print(if (sum(s1) == sum(s2)) "YES" else "NO")
}

fun sum(s: String): Int {
    return s.toList().sumOf { it.digitToInt() }
}