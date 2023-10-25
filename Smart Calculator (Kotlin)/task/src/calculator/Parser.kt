package calculator

private fun Char.isOp() = this in "+-*/^()"
private fun Char.isMinus() = this == '-'
private fun Char.isParenthesis() = this in "()"

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

    private var parenthCount = 0

    fun parse() {
        while (++currPos < s.length) {
            val c = s[currPos]
            when (tokenType) {
                TokenType.Unknown -> startToken(c)
                else -> tokenInProgress(c)
            }
        }
        if (parenthCount != 0) throw IllegalArgumentException("Parentheses are not paired")
    }

    private fun startToken(c: Char) {
        startPos = currPos
        tokenType = tokenTypeByChar(c)
    }

    private fun finishToken() {
        val tStr = s.substring(startPos, currPos)
        val token = CalcToken.tokenFactory(tokenType, tStr)
        _tokens += token
        if (token is CalcToken.Parenth) parenthCount += token.count
        tokenType = TokenType.Unknown
    }

    private fun tokenTypeByChar(c: Char) = when {
        c.isDigit() -> TokenType.Num
        c.isOp() -> TokenType.Operator
        c.isWhitespace() -> TokenType.Unknown
        else -> throw IllegalArgumentException("Invalid token!")
    }

    private fun tokenInProgress(c: Char) {
        val charToken = tokenTypeByChar(c)
        if (tokenType != charToken || c.isParenthesis() || s[currPos - 1].isParenthesis()) {
            // Check for unary minus or any other unary operator
            if (s[currPos - 1].isMinus() && charToken == TokenType.Num
                && (_tokens.isEmpty() || _tokens.last() is CalcToken.Operator)) {
                tokenType = TokenType.Num
            } else {
                finishToken()
                validateTokenOrder()
                startToken(c)
            }
        }
    }

    private fun validateTokenOrder() {
        if (_tokens.size > 1) {
            if (_tokens.last() is CalcToken.Number &&
                _tokens[tokens.lastIndex - 1] is CalcToken.Number)
                throw IllegalArgumentException("Wrong order of tokens!")
        }
    }
}