package calculator

abstract class CalcToken(val tStr: String) {

    class Number(s: String): CalcToken(s) {
        constructor(i: Int): this(i.toString())
        val value = tStr.toInt()
    }
    abstract class Operator(s: String): CalcToken(s) {
        abstract val value: String
        fun priority() = priorityMap[value]!!
        final override fun toString() = value
    }
    class Plus(s: String): Operator(s) {
        override val value = "+"
    }
    class Minus(s: String): Operator(s) {
        override val value = "-"
    }

    class Empty(s: String): CalcToken(s)

    override fun toString() = tStr

    companion object {
        /** Create a token based on the character type. */
        private fun plusToken(s: String) = Plus(s)
        private fun minusToken(s: String) = Minus(s)

        private val opMap = mapOf<Char, (String) -> CalcToken>(
            '+' to ::plusToken,
            '-' to ::minusToken
        )

        private val priorityMap = mapOf(
            "(" to 1,
            ")" to 1,
            "-" to 2,
            "+" to 2,
            "/" to 3,
            "*" to 3,
            "^" to 4
        )

        fun tokenFactory(type: TokenType, tStr: String) = when (type) {
            TokenType.Num -> Number(tStr)
            TokenType.Operator -> {
                checkOperatorUniformity(tStr)
                if (tStr.first() == '-' && tStr.length % 2 == 0) Plus(tStr)
                else opMap[tStr.first()]!!(tStr)
            }
            TokenType.Unknown -> Empty(tStr)
        }

        private fun checkOperatorUniformity(tStr: String) {
            if (tStr.count { it == tStr.first() } != tStr.length)
                throw IllegalArgumentException("Illegal argument for operator: $tStr")
        }
    }
}