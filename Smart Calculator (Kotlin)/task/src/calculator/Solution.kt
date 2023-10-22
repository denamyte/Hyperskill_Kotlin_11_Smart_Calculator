package calculator

import java.util.LinkedList

private val opMap = mapOf(
    "+" to {a: CalcToken.Number, b: CalcToken.Number -> CalcToken.Number(a.value + b.value)},
    "-" to {a: CalcToken.Number, b: CalcToken.Number -> CalcToken.Number(a.value - b.value)}
)

class Solution(val tokens: List<CalcToken>) {
    private val stack = LinkedList<CalcToken>()
    val postfix = mutableListOf<CalcToken>()

    private fun buildPostfix(): List<CalcToken> {
        tokens.forEach {
            when (it) {
                is CalcToken.Number -> postfix += it
                is CalcToken.Operator -> {
                    while (/*stack.isNotEmpty() &&*/
                        operatorOfGreaterOrEqualPriorityIsOnTheStack(it.priority())) {
                        postfix += stack.pop()
                    }
                    stack.push(it)
                }
                // todo: cases when a token is a left or right parenthesis
            }
        }
        while (stack.isNotEmpty()) postfix += stack.pop()
        return postfix.toList()
    }

    private fun operatorOfGreaterOrEqualPriorityIsOnTheStack(priority: Int): Boolean {
        val maxStackPriority = stack.filterIsInstance<CalcToken.Operator>().maxOfOrNull { it.priority() }
        return maxStackPriority != null && maxStackPriority >= priority
    }

    fun solve(): Int {
        buildPostfix()
        postfix.forEach {
            when (it) {
                is CalcToken.Number -> stack.push(it)
                is CalcToken.Operator -> {
                    val second = stack.pop()!! as CalcToken.Number
                    val first = stack.pop()!! as CalcToken.Number
                    stack.push(opMap[it.value]!!(first, second))
                }
            }
        }
        return (stack.pop() as CalcToken.Number).value
    }
}