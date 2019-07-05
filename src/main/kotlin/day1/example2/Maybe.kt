package day1.example2

sealed class Maybe<out A> {

    fun <B> map(f: (A) -> B): Maybe<B> = fold(none(), { a -> just(f(a)) })

    fun <B> flatMap(f: (A) -> Maybe<B>): Maybe<B> = fold(none(), f)

    fun <B> fold(ifEmpty: B, ifNotEmpty: (A) -> B): B =
        when (this) {
            is Just -> ifNotEmpty(this.value)
            is None -> ifEmpty
        }

    companion object {
        fun <A> just(value: A): Maybe<A> = Just(value)
        fun <A> none(): Maybe<A> = None
    }
}

fun <T> Maybe<T>.getOrElse(t: T): T = fold(t, ::identity)
data class Just<A>(val value: A) : Maybe<A>()
object None : Maybe<Nothing>()