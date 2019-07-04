package day1.example1

fun <A, B, C> ((A, B) -> C).curry(): (A) -> (B) -> C = { a -> { b -> this(a, b) } }

fun <A, B, C, D> ((A, B, C) -> D).curry(): (A) -> (B) -> (C) -> D = { a -> { b -> {c -> this(a, b, c) } }}

fun <A, B, C, D, E> ((A, B, C, D) -> E).curry(): (A) -> (B) -> (C) -> (D) -> E = { a -> { b -> {c -> { d -> this(a, b, c, d) } }}}


infix fun <A, B> ((A) -> B).`@`(a: A): B = this(a)


