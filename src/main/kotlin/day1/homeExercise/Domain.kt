package day1.homeExercise

import java.time.LocalDate

data class Employee(
    val lastName: String,
    val firstName: String,
    val birthDate: LocalDate,
    val emailAddress: EmailAddress
)

data class EmailAddress(val value: String)