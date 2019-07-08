package day1.exercise3

import java.lang.UnsupportedOperationException

sealed class Operation<T> {
    abstract fun op(): T
}

data class SumInt(val x: Int, val y: Int): Operation<Int>() {
    override fun op(): Int = x + y
}

object NoOp: Operation<Nothing>() {

    override fun op(): Nothing {
        throw UnsupportedOperationException()
    }
}

data class MultInt(val x: Int, val y: Int): Operation<Int>() {
    override fun op(): Int = x * y
}

data class Calculator(
    val writer: (String) -> Console<Unit>,
    val reader: () -> Console<String>
){
    fun operation() {

        val expression = reader().exec().split(" ")
        val (op, x, y) = expression

        val operation = when(op) {
            "+" -> SumInt(x.toInt(), y.toInt())
            "*" -> MultInt(x.toInt(), y.toInt())
            else -> NoOp
        }

        writer(operation.op().toString())
    }

}
