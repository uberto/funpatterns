package day1.example3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class OutcomeTest {

    data class DbError(val msg: String)

    data class Order(val id: Int, val userId: Int, val amount: Double)

    data class User(val id: Int, val name: String)


    fun readOrderFromDb(orderId: Int): Outcome<DbError, Order> =
        if (orderId == existingOrderId)
            Success(Order(existingOrderId, 123, 123.4))
        else
            Failure(DbError("order $orderId does not exist"))

    fun readUserFromDb(id: Int): Outcome<DbError, User> = Success(User(id, "Joe"))

    val existingOrderId = 123
    val notExistingOrderId = 234

    @Test
    fun `check for error cases`() {

        val res = readOrderFromDb(notExistingOrderId)
        when (res) {
            is Success -> fail("this order doesn't exit")
            is Failure -> assertThat(res.error).isEqualTo(DbError("order $notExistingOrderId does not exist"))
        }

    }

    @Test
    fun `map results`() {
        val amount: Double = readOrderFromDb(existingOrderId)
            .map { o -> o.amount }
            .onFailure { return }

        assertThat(amount).isEqualTo(123.4)
    }


    @Test
    fun `combine results`() {
        val userName: String = readOrderFromDb(existingOrderId)
            .flatMap { o -> readUserFromDb(o.userId) }
            .map { it.name }
            .onFailure { fail("Order and User should exist") }

        assertThat(userName).isEqualTo("Joe")
    }
}