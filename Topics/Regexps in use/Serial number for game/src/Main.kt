fun findSerialNumberForGame(listGames: List<String>) =
    readln().run {
        listGames.find { it.startsWith(this) }
            .let {
                it?.split("@")?.let { list ->
                    println("Code for Xbox - ${list[1]}, for PC - ${list[2]}")
                }
            }
    }
