package day1.example2


infix fun String?.`++`(other: String?): String? =
    if (this == null)
        other
    else if (other == null)
        this
    else
        this + other

fun Int.fizz(): String? = "Fizz".takeIf{ this % 3 == 0 }

fun Int.buzz(): String? = "Buzz".takeIf{ this % 5 == 0 }


fun fizzBuzz(x: Int): String = x.fizz() `++` x.buzz() ?: x.toString()
