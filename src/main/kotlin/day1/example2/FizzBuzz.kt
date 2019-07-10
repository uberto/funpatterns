package day1.example2

fun fizz(x: Int): String? = "Fizz".takeIf { x % 3 == 0 }
fun buzz(x: Int): String? = "Buzz".takeIf { x % 5 == 0 }

fun ((Int) -> String?).combine(other: (Int) -> String?): (Int) -> String? = { n ->
    when {
        this(n) == null -> other(n)
        other(n) == null -> this(n)
        else -> this(n) + other(n)
    }
}

fun ((Int) -> String?).orElse(f: (Int) -> String): (Int) -> String = { n ->
    this(n) ?: f(n)
}

val fizzBuzz = ::fizz.combine(::buzz).orElse { it.toString() }
