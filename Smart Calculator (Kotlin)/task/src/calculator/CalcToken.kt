package calculator

import java.math.BigInteger

abstract class CalcToken(val tStr: String) {

    class Number(s: String): CalcToken(s) {
        constructor(i: BigInteger): this(i.toString())
        val value = tStr.toBigInteger()
    }
    abstract class Operator(s: String): CalcToken(s) {
        val value = with(s) {
            if (startsWith('-') && length % 2 == 0) "+" else substring(0, 1)
        }
        val priority = priorityMap[value]!!
        final override fun toString() = value
    }
    class Plus(s: String): Operator(s)
    class Minus(s: String): Operator(s)
    abstract class HigherOp(s: String) : Operator(s) {
        init {
            if (s.length > 1)
                throw IllegalArgumentException("Multiple chars are not allowed in this operator")
        }
    }
    class Multiply(s: String) : HigherOp(s)
    class Divide(s: String) : HigherOp(s)
    class Power(s: String) : HigherOp(s)
    abstract class Parenth(s: String): Operator(s) {
        abstract val count: Int
    }
    class OpenParenth: Parenth("(") {
        override val count = 1
    }
    class CloseParenth: Parenth(")") {
        override val count = -1
    }

    class Empty(s: String): CalcToken(s)

    override fun toString() = tStr

    companion object {
        /** Create a token based on the character type. */
        private fun plusToken(s: String) = Plus(s)
        private fun minusToken(s: String) = Minus(s)
        private fun multiplyToken(s: String) = Multiply(s)
        private fun divideToken(s: String) = Divide(s)
        private fun powerToken(s: String) = Power(s)
        private fun openToken(s: String) = OpenParenth()
        private fun closeToken(s: String) = CloseParenth()

        private val opMap = mapOf<Char, (String) -> CalcToken>(
            '+' to ::plusToken,
            '-' to ::minusToken,
            '*' to ::multiplyToken,
            '/' to ::divideToken,
            '^' to ::powerToken,
            '(' to ::openToken,
            ')' to ::closeToken,
        )

        private val priorityMap = mapOf(
            "^" to 4,
            "/" to 3,
            "*" to 3,
            "-" to 2,
            "+" to 2,
            "(" to 1,
            ")" to 1,
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