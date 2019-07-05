package day1.exercise2


fun compactJson(json: Sequence<Char>): String =
    json.fold(OutQuotes(""), ::compactor).jsonCompacted


fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)




sealed class JsonCompactor{
    abstract val jsonCompacted: String
    abstract fun compact(c: Char): JsonCompactor
}

data class InQuotes(override val jsonCompacted: String): JsonCompactor() {
    override fun compact(c: Char): JsonCompactor =
        if (c == '"')
            OutQuotes(jsonCompacted+c)
        else
            InQuotes(jsonCompacted+c)
}

data class OutQuotes(override val jsonCompacted: String): JsonCompactor() {
    override fun compact( c: Char): JsonCompactor =
        if ( c in arrayOf(' ', '\n', '\t'))
            this
        else if (c == '"')
            InQuotes(jsonCompacted+c)
        else
            OutQuotes(jsonCompacted+c)
}