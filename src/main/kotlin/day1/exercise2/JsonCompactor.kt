package day1.exercise2


fun compactJson(json: Sequence<Char>): String =
    json.fold(ClosedQuoteJsonCompactor(""), ::compactor).newJson


fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)

sealed class JsonCompactor {
    abstract val newJson: String
    abstract fun compact(c: Char): JsonCompactor
}

data class OpenQuoteJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor =
        when (c) {
            '"' -> ClosedQuoteJsonCompactor(newJson + "\"")
            else -> OpenQuoteJsonCompactor(newJson + c)
        }

}

data class ClosedQuoteJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor =
        when (c) {
            '\t' -> ClosedQuoteJsonCompactor(newJson)
            '\n' -> ClosedQuoteJsonCompactor(newJson)
            ' ' -> ClosedQuoteJsonCompactor(newJson)
            '"' -> OpenQuoteJsonCompactor(newJson + "\"")
            else -> ClosedQuoteJsonCompactor(newJson + c)
        }

}