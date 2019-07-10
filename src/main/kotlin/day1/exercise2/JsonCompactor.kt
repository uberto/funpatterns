package day1.exercise2


fun compactJson(json: Sequence<Char>): String =
    json.fold(TODO(), ::compactor).newJson


fun compactor(prev: JsonCompactor, c: Char): JsonCompactor =
    prev.compact(c)


sealed class JsonCompactor {
    abstract val newJson: String
    abstract fun compact(c: Char): JsonCompactor
}