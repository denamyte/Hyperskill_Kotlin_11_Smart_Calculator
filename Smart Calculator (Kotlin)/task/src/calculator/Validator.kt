package calculator

class Validator {

    class Parts {
        var errorString: String? = null
        val isError get() = errorString != null
        var firstStr: String = ""
        var secondStr: String? = null
        val isAssignment get() = secondStr != null

        var expression: String
            get() = if (isAssignment) secondStr!! else firstStr
            set(value) {
                if (isAssignment) secondStr = value
                else firstStr = value
            }
    }
    companion object {
        private val VAR_REGEX = Regex("""\b[a-zA-Z]+\b""")
        private val INVALID_VAR_REGEX = Regex(""" *([a-zA-Z]\d|\d[a-zA-Z]).*""")

        /** Validate an expression and return an error string if it is not valid. */
        fun validate(input: String, vars: Variables): Parts {
            val parts = Parts()
            val split = input.split("=")
            when (split.size) {
                1 -> parts.firstStr = input.trim()
                else -> {
                    parts.firstStr = split[0].trim()
                    parts.secondStr = input.substring(split[0].length + 1).trim()
                }
            }
            validate(vars, parts)
            return parts
        }

        private fun validate(vars: Variables, parts: Parts) {
            when {  // Check invalid identifiers
                parts.isAssignment && !VAR_REGEX.matches(parts.firstStr) -> {
                    parts.errorString = "Invalid identifier"
                    return
                }
                !parts.isAssignment -> {
                    val firstToken = parts.firstStr.split(Regex("\\W"))[0]
                    if (firstToken.isNotEmpty() && firstToken[0].isLetter() && !VAR_REGEX.matches(firstToken)) {
                        parts.errorString = "Invalid identifier"
                        return
                    }
                }
            }
            parts.expression = vars.replaceVariablesWithValues(parts.expression)
            when {
                INVALID_VAR_REGEX.find(parts.expression) != null ||
                        parts.expression.contains("=") -> {
                    parts.errorString = "Invalid assignment"
                    return
                }
                VAR_REGEX.find(parts.expression) != null -> {
                    parts.errorString = "Unknown variable"
                    return
                }
            }
        }
    }
}