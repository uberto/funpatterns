package day1.exercise3

data class Calculator(
    val writer: (String) -> Console<Unit>,
    val reader: () -> Console<String>
) {

    operator fun invoke() {
        val cmd: String = reader().exec()

        val elements = cmd.split(" ")
        val res = if (elements.size != 3) {
            "Syntax Error $cmd"
        } else {
            operation(elements[0], elements[1], elements[2])
        }
        writer(res).exec()
    }

    fun operation(op: String, a: String, b: String): String {
        val x = a.toDouble()
        val y = b.toDouble()
        val res = when (op) {
            "+" -> x + y
            "-" -> x - y
            "*" -> x * y
            "/" -> x / y
            else -> "Unknown operation $op"
        }
        return res.toString()
    }
}

fun main() {
    val calc = Calculator(::printIO, ::readlineIO)

    while (true) {
        calc()
    }
}

