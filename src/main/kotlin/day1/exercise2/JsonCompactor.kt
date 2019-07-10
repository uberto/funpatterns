package day1.exercise2

fun compactJson(json: Sequence<Char>): String =
    json.fold(OutOfQuote(""), ::compactor).newJson

fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)

fun String.append(other: Char): String = this + other

sealed class JsonCompactor {
    val chars = listOf(' ', '\n', '\t')
    val quote = '"'

    abstract val newJson: String
    abstract fun compact(c: Char): JsonCompactor
}

data class OutOfQuote(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor =
        when {
            c == quote -> InQuote(newJson.append(c))
            chars.contains(c) -> this
            else -> OutOfQuote(newJson.append(c))
        }
}

data class InQuote(override val newJson: String) : JsonCompactor() {


    override fun compact(c: Char): JsonCompactor =
        if (c == quote) OutOfQuote(newJson.append(c))
        else InQuote(newJson.append(c))
}
