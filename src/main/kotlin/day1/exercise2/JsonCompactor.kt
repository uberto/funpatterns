package day1.exercise2


fun compactJson(json: Sequence<Char>): String =
    json.fold( MyJsonCompactor(""), ::compactor).newJson


fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)


val blackListChar = listOf(' ', '\n', '\t')

sealed class JsonCompactor{
    abstract val newJson: String
    abstract fun compact(c: Char): JsonCompactor
}

data class MyJsonCompactor(override val newJson: String) : JsonCompactor() {

    override fun compact(c: Char): JsonCompactor {
        val count = newJson.count { it == '"' }
        val result = if(count % 2 == 0 && blackListChar.contains(c)) newJson
            else newJson + c

        return MyJsonCompactor(result)
    }

}