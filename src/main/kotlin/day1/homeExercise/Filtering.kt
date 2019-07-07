package day1.homeExercise

import java.time.LocalDate
import java.time.Month

class IsEmployeeBirthdayToday(private val today: LocalDate): (Employee) -> Boolean {

    override fun invoke(e: Employee): Boolean =
        when {
            e.birthDate.`is`(Month.FEBRUARY, 29) && today.isLeapYear -> today.`is`(Month.FEBRUARY, 29)
            e.birthDate.`is`(Month.FEBRUARY, 29)  -> today.`is`(Month.FEBRUARY, 28)
            else -> today.isSameDateAs(e.birthDate)
        }


    private fun LocalDate.isSameDateAs(other: LocalDate) =
        other.month == this.month && other.dayOfMonth == this.dayOfMonth


    private fun LocalDate.`is`(month: Month, dayOfMonth: Int): Boolean =
        this.month == month && this.dayOfMonth == dayOfMonth

}