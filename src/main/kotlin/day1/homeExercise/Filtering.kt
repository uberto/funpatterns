package day1.homeExercise

import java.time.LocalDate

class IsEmployeeBirthdayToday(val today: LocalDate): (Employee) -> Boolean {

    override fun invoke(e: Employee): Boolean =
        e.birthDate.month == today.month && e.birthDate.dayOfMonth == today.dayOfMonth

}