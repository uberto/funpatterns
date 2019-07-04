package day1.exercise2


fun compactJson(json: Sequence<Char>): String =
    json.fold(NoOpenQuotesJsonCompactor(""), ::compactor).newJson


fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)

sealed class JsonCompactor {
    abstract val newJson: String
    abstract fun compact(c: Char): JsonCompactor
}

data class OpenQuotesJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor =
        when (c) {
            '"' -> NoOpenQuotesJsonCompactor(newJson + "\"")
            else -> OpenQuotesJsonCompactor(newJson + c)
        }

}

data class NoOpenQuotesJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor =
        when (c) {
            '\t' -> NoOpenQuotesJsonCompactor(newJson)
            '\n' -> NoOpenQuotesJsonCompactor(newJson)
            ' ' -> NoOpenQuotesJsonCompactor(newJson)
            '"' -> OpenQuotesJsonCompactor(newJson + "\"")
            else -> NoOpenQuotesJsonCompactor(newJson + c)
        }

}