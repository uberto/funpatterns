package day1.example3

import org.junit.jupiter.api.Test

class OutcomeTest {

    data class DbError(override val msg: String) : Error

    data class Order(val id: Int, val userId: Int, val amount: Double)

    data class User(val id: Int, val name: String)


    fun readOrderFromDb(orderId: Int): Outcome<DbError, Order> = TODO()

    fun readUserFromDb(id: Int): Outcome<DbError, User> = TODO()


    @Test
    fun `check for error cases`() {

        val res = readOrderFromDb(123)
        return when (res) {
            is Success -> TODO()
            is Failure -> TODO()
        }

    }

    @Test
    fun `map results`() {
        val amount: Double = readOrderFromDb(123)
            .map { o -> o.amount }
            .onFailure { return }


    }


    @Test
    fun `combine results`() {
        val userName: String = readOrderFromDb(123)
            .flatMap { o -> readUserFromDb(o.userId) }
            .map { it.name }
            .onFailure { return }


    }
}