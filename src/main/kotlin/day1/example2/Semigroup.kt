package day1.example2

interface Semigroup<A> {
    fun combine(a: A, b: A): A

    companion object {
        fun ofStrings(): Semigroup<String> = object : Semigroup<String> {
            override fun combine(a: String, b: String): String = a + b
        }

        fun <A> ofMaybes(s: Semigroup<A>): Semigroup<Maybe<A>> = object :
            Semigroup<Maybe<A>> {
            override fun combine(a: Maybe<A>, b: Maybe<A>): Maybe<A> =
                a.fold(
                    b,
                    { valA ->
                        b.fold(
                            Maybe.just(valA),
                            { valB -> Maybe.just(s.combine(valA, valB)) }
                        )
                    }
                )
        }
    }
}