fun main() = println(readln().charPercentage('c', 'g'))

private fun String.charPercentage(vararg chars: Char) = lowercase().count { it in chars } * 100.0 / length