package day1.homeExercise

import org.junit.jupiter.api.Test


class BirthdayKataTest {


    @Test
    fun `happy path`(){

        sendGreetingsToAll("fixtures/bigFile.csv")

        TODO("Assert emails sent")
    }


    @Test
    fun `csv file with errors`(){
        sendGreetingsToAll("fixtures/wrongFile.csv")

        TODO("Assert correct error")
    }


}
