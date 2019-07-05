package day1.homeExercise

import day1.example3.Outcome
import day1.example3.ThrowableError
import java.io.FileReader
import java.time.LocalDate
import java.util.stream.Collectors


//see http://matteo.vaccari.name/blog/archives/154 for the Kata rules

fun reader(fileName: String) : Outcome<ThrowableError, CSV> =
    Outcome.tryThis { FileReader(fileName)
        .readLines().stream()
        .skip(1)
        .map { it.split(", ") }
        .collect(Collectors.toList())  }

fun sender(employee: Employee): Outcome<ThrowableError, Unit> = Outcome.tryThis { println(employee) }

fun sendGreetingsToAll(filename: String, xDate: LocalDate): Outcome<ThrowableError, List<Outcome<ThrowableError, Unit>>> =
    BirthdayService.program(::reader, ::sender, xDate)(filename)

