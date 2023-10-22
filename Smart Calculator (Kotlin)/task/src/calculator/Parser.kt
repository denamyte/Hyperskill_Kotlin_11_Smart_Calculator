package calculator

private fun Char.isPlus() = this == '+'
private fun Char.isMinus() = this == '-'
private fun Char.isOp() = isPlus() || isMinus()

class Parser(s: String) {

    /** The string to parse */
    private val s = "$s "  // Add a space for the parser to properly finish its work
    /** The position where a token starts */
    private var startPos = -1
    /** The current index in the string */
    private var currPos = -1
    private var tokenType: TokenType = TokenType.Unknown

    private var _tokens = mutableListOf<CalcToken>()
    val tokens get() = _tokens.toList()

    fun parse() {
        while (++currPos < s.length) {
            val c = s[currPos]
            when (tokenType) {
                TokenType.Unknown -> startToken(c)
                else -> tokenInProgress(c)
            }
        }
    }

    private fun startToken(c: Char) {
        startPos = currPos
        tokenType = tokenTypeByChar(c)
    }

    private fun finishToken() {
        val tStr = s.substring(startPos, currPos)
        _tokens += CalcToken.tokenFactory(tokenType, tStr)
        tokenType = TokenType.Unknown
    }

    private fun tokenTypeByChar(c: Char) = when {
        c.isDigit() -> TokenType.Num
        c.isOp() -> TokenType.Operator
        else/*c.isWhitespace()*/ -> TokenType.Unknown
    }

    private fun tokenInProgress(c: Char) {
        val charToken = tokenTypeByChar(c)
        if (tokenType != charToken) {
            // Check for unary minus or any other unary operator
            if (tokenType == TokenType.Operator && charToken == TokenType.Num
                && (_tokens.isEmpty() || _tokens.last() is CalcToken.Operator)) {
                tokenType = TokenType.Num
            } else {
                finishToken()
                startToken(c)
            }
        }
    }
}