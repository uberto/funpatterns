package day1.homeExercise

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isFalse
import org.junit.jupiter.api.Test
import java.time.LocalDate


class BirthdayKataTest {


    @Test
    fun `happy path`(){

        val result = sendGreetingsToAll("/Users/giadafinocchiaro/IdeaProjects/funpatterns/fixtures/bigFile.csv",
            LocalDate.of(2019, 10, 8))

        assertThat( result.isFailure() ).isFalse()
        assertThat( result.fold().filter { it.isFailure() } ).isEmpty()

    }


    @Test
    fun `csv file with errors`(){
        sendGreetingsToAll("fixtures/wrongFile.csv", LocalDate.now())

        TODO("Assert correct error")


    }


}
