// You can experiment here, it won’t be checked

fun modifyString(initialString: String): String{
    var modifiedString = initialString

    modifiedString = modifiedString.removeRange(2, 10) // 1
    if (modifiedString.contains(" ")) // 2
        modifiedString += "ABC"
    modifiedString = modifiedString.substringAfter(" ") + modifiedString.substringBefore(" ") // 3
    if (modifiedString.contains("a")) // 4
        modifiedString.plus("1248")
    modifiedString = modifiedString.replaceFirst(" ", "$") // 5
    if (modifiedString.length < 15) // 6
        modifiedString = modifiedString.reversed()
    modifiedString += "18B20" // 7
    modifiedString = modifiedString.substringAfter("1") + modifiedString.substringBefore("5") // 8
    modifiedString.dropLast(4) // 9

    return  modifiedString
}

fun main() {
    val password = modifyString("my password is 12345")
    println(password)
}