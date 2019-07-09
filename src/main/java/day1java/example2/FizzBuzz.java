package day1java.example2;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FizzBuzz {

    private static List<Function<Integer, Optional<String>>> rules = new LinkedList<Function<Integer, Optional<String>>>(){{
        add(FizzBuzz::fizz);
        add(FizzBuzz::buzz);
    }};

    static String fizzBuzz(int x) {
        Semigroup<Optional<String>> optionalSemigroup = optionalSemigroup(stringSemigroup);
        return rules
                .stream()
                .map(rule -> rule.apply(x))
                .reduce(Optional.empty(), optionalSemigroup::combine)
                .orElse(String.valueOf(x));
    }

    static Optional<String> fizz(int x) {
        return Optional.ofNullable(word(3, "Fizz").apply(x));
    }

    static Optional<String> buzz(int x) {
        return Optional.ofNullable(word(5, "Buzz").apply(x));
    }

    private static Function<Integer, String> word(int divideBy, String word) {
        return x -> {
            if (x % divideBy == 0)
                return word;
            else
                return null;
        };
    }

    interface Semigroup<T> {
        T combine(T a, T b);
    }

    private static Semigroup<String> stringSemigroup = (a, b) -> a + b;

    private static <T> Semigroup<Optional<T>> optionalSemigroup(Semigroup<T> innerSemigroup) {
        return (a, b) ->
                a.map(valA ->
                        b.map(valB ->
                                Optional.of(innerSemigroup.combine(valA, valB))
                        ).orElse(a)
                ).orElse(b);
    }
}
