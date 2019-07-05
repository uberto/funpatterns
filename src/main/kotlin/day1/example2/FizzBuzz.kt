package day1.example2


fun fizzBuzz(x: Int): String = fizz(x).compose(buzz(x)) ?: x.toString()

fun fizz(x: Int): String? = "Fizz".takeIf { x % 3 == 0 }
fun buzz(x: Int): String? = "Buzz".takeIf { x % 5 == 0 }

// because null + null generate a nullnull string I must compose with logic
fun String?.compose (other: String?): String? =
    when {
        this == null -> other
        other == null -> this
        else -> this.toString() + other.toString()
    }