package day1.example3


sealed class  Outcome<out E, out T: Any> {

    fun <U: Any> map(f: (T) -> U): Outcome<E, U> =
        when (this){
            is Success -> Success(f(this.value))
            is Failure -> this
        }

    fun <U> mapFailure(f: (E) -> U): Outcome<U, T> =
        when (this){
            is Success -> this
            is Failure -> Failure(f(this.error))
        }

    fun <B> fold(ifEmpty: (E) -> B, ifNotEmpty: (T) -> B): B =
        when (this){
            is Success -> ifNotEmpty(this.value)
            is Failure -> ifEmpty(this.error)
        }


    companion object {
        fun <T: Any> tryThis(block: () -> T): Outcome<Throwable, T> =
            try {
                Success(block())
            } catch (e: Throwable){
                Failure(e)
            }
    }
}

data class Success<T: Any>(val value: T): Outcome<Nothing, T>()
data class Failure<E>(val error: E): Outcome<E, Nothing>()


inline fun <T: Any, U: Any, E> Outcome<E, T>.flatMap(f: (T) -> Outcome<E, U>): Outcome<E, U> =
    when (this) {
        is Success<T> -> f(value)
        is Failure<E> -> this
    }

inline fun <E, T: Any>Outcome<E, T>.mapNullableError(f: (T) -> E?): Outcome<E, Unit> =
    when (this){
        is Success<T> -> {
            val error = f(this.value)
            if (error == null ) Success(Unit) else Failure(error)
        }
        is Failure<E> -> this
    }

inline fun <T: Any, E> Outcome<E, T>.onFailure(block: (E) -> T): T = // acts like a recover from failure
    when (this) {
        is Success<T> -> value
        is Failure<E> -> block(error)
    }