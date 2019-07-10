package day1.example3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class OutcomeTest {

    data class DbError(override val msg: String) : Error

    data class Order(val id: Int, val userId: Int, val amount: Double)

    data class User(val id: Int, val name: String)


    fun readOrderFromDb(orderId: Int): Outcome<DbError, Order> =
        if (orderId == 123) Failure(DbError("order not found"))
        else Success(Order(orderId, 42, 123.0))


    fun readUserFromDb(id: Int): Outcome<DbError, User> =
        if (id == 555) Failure(DbError("user not found"))
        else Success(User(id, "foo"))


    @Test
    fun `check for error cases`() {
        readOrderFromDb(123)
            .onFailure {
                return assertThat(it).isEqualTo(DbError("order not found"))
            }
       
        fail("123 must be not found")
    }

    @Test
    fun `map results`() {
        val amount: Double = readOrderFromDb(1234)
            .map { o -> o.amount }
            .onFailure { fail("Outcome must be Success") }

        assertThat(amount).isEqualTo(123.0)
    }


    @Test
    fun `combine results`() {
        val userName: String = readOrderFromDb(1234)
            .flatMap { o -> readUserFromDb(o.userId) }
            .map { it.name }
            .onFailure { fail("Outcome must be Success") }

        assertThat(userName).isEqualTo("foo")
    }
}