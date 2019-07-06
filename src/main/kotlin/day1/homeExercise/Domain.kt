package day1.homeExercise

import java.time.LocalDate

data class Employee(
    val lastName: String,
    val firstName: String,
    val birthDate: LocalDate,
    val email: Email
)

data class Email(val value: String)