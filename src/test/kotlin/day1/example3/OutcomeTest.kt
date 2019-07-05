package day1.example3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OutcomeTest {

    data class DbError(override val msg: String): Error

    data class Order(val id: Int, val userId: Int, val amount: Double)

    data class User(val id: Int, val name: String)


    fun readOrderFromDb(orderId: Int): Outcome<DbError, Order> = Success(Order(orderId, 1, 1.0))

    fun readUserFromDb(id: Int): Outcome<DbError, User> = Success(User(id, "John"))


    @Test
    fun `check for error cases`(){

        val res = readOrderFromDb(123)
        return when(res){
            is Success -> assertThat (res.value.id ).isEqualTo(123)
            is Failure -> Assertions.fail()
        }

    }

    @Test
    fun `map results`(){
        val amount: Double = readOrderFromDb(123)
            .map { o -> o.amount }
            .onFailure { return }


    }


    @Test
    fun `combine results`(){
        val userName: String = readOrderFromDb(123)
            .flatMap { o -> readUserFromDb(o.userId) }
            .map {it.name}
            .onFailure { return }


    }
}