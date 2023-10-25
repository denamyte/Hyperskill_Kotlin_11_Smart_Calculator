package calculator

import java.util.LinkedList
import kotlin.math.pow

private val opMap = mapOf<String, (CalcToken.Number, CalcToken.Number) -> CalcToken.Number>(
    "+" to {a, b -> CalcToken.Number(a.value + b.value)},
    "-" to {a, b -> CalcToken.Number(a.value - b.value)},
    "*" to {a, b -> CalcToken.Number(a.value * b.value)},
    "/" to {a, b -> CalcToken.Number(a.value / b.value)},
    "^" to {a, b -> CalcToken.Number(a.value.toDouble().pow(b.value).toInt())},
)

class Solution(private val tokens: List<CalcToken>) {
    private val postfix = mutableListOf<CalcToken>()

    private fun buildPostfix(): List<CalcToken> {
        val stack = LinkedList<CalcToken.Operator>()
        tokens.forEach {
            when (it) {
                is CalcToken.Number -> postfix += it
                is CalcToken.OpenParenth -> stack.push(it)
                is CalcToken.CloseParenth /* ")" */ -> {
                    while (stack.peek() !is CalcToken.OpenParenth)
                        postfix += stack.pop()
                    stack.pop()  // "("
                }
                is CalcToken.Operator -> {
                    if (stack.isEmpty()) {
                        stack.push(it)
                    } else {
                        val stackTop = stack.peek()
                        if (stackTop is CalcToken.OpenParenth)
                            stack.push(it)
                        else if (it.priority > stackTop.priority)
                            stack.push(it)
                        else /* curOp.priority <= stackTop.priority */ {
                            while (stack.isNotEmpty() &&
                                (stack.peek() !is CalcToken.OpenParenth && it.priority <= stack.peek().priority))
                                postfix += stack.pop()
                            stack.push(it)
                        }
                    }
                }
            }
        }
        while (stack.isNotEmpty()) postfix += stack.pop()
        return postfix.toList()
    }

    fun solve(): Int {
        buildPostfix()
        val stack = LinkedList<CalcToken>()
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