package calculator

class Variables {
    private val valueMap = mutableMapOf<String, String>()

    fun replaceVariablesWithValues(expr: String): String {
        var s = expr
        valueMap.forEach { (k, v) -> s = s.replace(Regex("""\b$k\b"""), v) }
        return s
    }

    fun setVariable(variable: String, value: String) {
        valueMap[variable] = value
    }
}