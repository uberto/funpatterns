package day1.example1

fun <A,B,C> ((A, B) -> C).curry(): (A) -> (B) -> C = TODO()

fun <A,B,C,D> ((A, B, C) -> D).curry(): (A) -> (B) -> (C) -> D = TODO()

fun <A,B,C,D, E> ((A, B, C, D) -> E).curry(): (A) -> (B) -> (C) -> (D) -> E  = TODO()



infix fun <A,B> ((A) -> B).`@`(a: A): B = TODO()


