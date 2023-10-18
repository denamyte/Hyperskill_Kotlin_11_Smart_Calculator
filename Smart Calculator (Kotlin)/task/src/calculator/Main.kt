package calculator

fun main() {
    readln().split(" ").map(String::toInt).sum().let(::println)
}
