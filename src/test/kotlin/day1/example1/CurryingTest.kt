package day1.example1

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.util.*

class CurryingTest {

    fun strConcat(s1: String, s2: String) = s1+s2

    data class Person( val creation: Date, val isCustomer: Boolean, val id: Int, val name: String )

    @Test
    fun `currying concat`(){

        val starPrefix = ::strConcat.curry()("*")

        assertThat(starPrefix("abc")).isEqualTo("*abc")

    }


    @Test
    fun `currying Person`(){

        val now = Date()
        val fred =
            ::Person.curry()   `@` now `@` false `@` 4 `@` "Fred"

        val fredOrig =
            Person(now,false, 4,"Fred")

        assertThat(fred).isEqualTo(fredOrig)

    }

    @Test
    fun `creating People`(){

        val personPartBuilder =
            ::Person.curry()   `@` Date() `@` true

        val name = listOf("Fred", "Mary", "Ann", "Bob")

        val people = name.mapIndexed{ i, name ->
            personPartBuilder(i)(name)
        }

        println(people)

        assertThat(people).hasSize(name.size)

    }

}