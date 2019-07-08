package day1.exercise2


fun compactJson(json: Sequence<Char>): String =
    json.fold( OutsideQuoteJsonCompactor(""), ::compactor).newJson


fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)


val blackListChar = listOf(' ', '\n', '\t')

sealed class JsonCompactor{
    abstract val newJson: String
    abstract fun compact(c: Char): JsonCompactor
}

data class InsideQuoteJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor = when(c) {
            '"' -> OutsideQuoteJsonCompactor(newJson+c)
            else -> InsideQuoteJsonCompactor(newJson + c)
        }
}

data class OutsideQuoteJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor = when(c) {
            '"' -> InsideQuoteJsonCompactor(newJson+c)
            in blackListChar -> OutsideQuoteJsonCompactor(newJson)
            else -> OutsideQuoteJsonCompactor(newJson + c)
        }
}