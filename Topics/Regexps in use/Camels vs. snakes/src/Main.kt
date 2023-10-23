fun getCamelStyleString(inputString: String): String =
    inputString.split("_")
        .joinToString("") { it.replaceFirstChar(Char::uppercase) }
