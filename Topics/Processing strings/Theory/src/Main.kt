// You can experiment here, it won’t be checked

fun main(args: Array<String>) {
    var s = "=big =b+"
    val name = "b"
    s = s.replace(Regex("""\b$name\b"""), "4")
    print(s)
}
