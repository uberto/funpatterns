package day1.example2

infix fun String?.compose (other: String?): String? =
    if (this == null) other
    else if (other == null) this
    else this.toString() + other.toString()

fun fizz(x:Int): String? = "Fizz".takeIf{x % 3 == 0}

fun buzz(x:Int): String? = "Buzz".takeIf{x % 5 == 0}

fun fizzBuzz(x: Int): String = fizz(x) compose buzz(x) ?: x.toString()


