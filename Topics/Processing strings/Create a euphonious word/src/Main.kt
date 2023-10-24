fun main() {
    val vowels = listOf('a', 'e', 'i', 'o', 'u', 'y')
    val s = readln()
    var counter = 0
    var countVowels = true
    val counters = mutableListOf<Int>()
    for (ch in s) {
        if (ch in vowels && countVowels ||
            ch !in vowels && !countVowels) {
            counter++
        } else {
            counters.add(counter)
            counter = 1
            countVowels = !countVowels
        }
    }
    counters.add(counter)

    val changes = counters.filter { it > 0 }.sumOf { (it + 1) / 2 - 1 }

    println(changes)
}