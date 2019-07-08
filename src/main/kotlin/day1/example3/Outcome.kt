package day1.example3

import java.lang.Exception
import java.util.*


sealed class Outcome<out E: Error, out T: Any> {


    fun <U: Any> map(f: (T) -> U): Outcome<E, U> =
        when (this){
            is Success -> Success(f(this.value))
            is Failure -> this
        }

    fun <U: Error> mapFailure(f: (E) -> U): Outcome<U, T> =
        when (this){
            is Success -> this
            is Failure -> Failure(f(this.error))
        }

    abstract fun isFailure(): Boolean
    abstract fun fold(): T

    companion object {
        fun <T: Any> tryThis(block: () -> T): Outcome<ThrowableError, T> =
            try {
                Success(block())
            } catch (e: Throwable){
                Failure(ThrowableError(e))
            }
    }
}

data class Success<T: Any>(val value: T): Outcome<Nothing, T>() {
    override fun fold(): T = value
    override fun isFailure(): Boolean = false
}

data class Failure<E: Error>(val error: E): Outcome<E, Nothing>() {
    override fun fold(): Nothing = throw Exception(error.msg)
    override fun isFailure(): Boolean = true
}


inline fun <T: Any, U: Any, E: Error> Outcome<E, T>.flatMap(f: (T) -> Outcome<E, U>): Outcome<E, U> =
    when (this) {
        is Success<T> -> f(value)
        is Failure<E> -> this
    }

inline fun <E: Error, T: Any>Outcome<E, T>.mapNullableError(f: (T) -> E?): Outcome<E, Unit> =
    when (this){
        is Success<T> -> {
            val error = f(this.value)
            if (error == null ) Success(Unit) else Failure(error)
        }
        is Failure<E> -> this
    }

inline fun <T: Any, E: Error> Outcome<E, T>.onFailure(block: (E) -> Nothing): T =
    when (this) {
        is Success<T> -> value
        is Failure<E> -> block(error)
    }

fun <T: Any, E: Error> Outcome<E, T>.toOptional(): Optional<T> =
    when (this) {
        is Success<T> -> Optional.of(value)
        is Failure<E> -> Optional.empty()
    }

interface Error{
    val msg: String
}

data class ThrowableError(val t: Throwable): Error {
    override val msg: String
        get() = t.message.orEmpty()
}

fun <T: Error> T.toFailure(): Outcome<T, Nothing> = Failure(this)