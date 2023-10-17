data class Block(val color: String) {
    object BlockProperties {
        var length = 0
        var width = 0

        fun blocksInBox(h: Int, w: Int) = h / length * (w / width)
    }
}

