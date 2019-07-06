package day1java.example2;

import java.util.Optional;

public class FizzBuzz {


    static String fizzBuzz(int x) {
        return combine(fizz(x), buzz(x)).orElse(String.valueOf(x));
    }

    static Optional<String> combine(Optional<String> a, Optional<String> b) {
        if (a.isEmpty())
            return b;
        else if (b.isEmpty())
            return a;
        else
            return Optional.of(a.get() + b.get());
    }

    static Optional<String> fizz(int x) {
        if (x % 3 == 0)
            return Optional.of("Fizz");
        else
            return Optional.empty();
    }

    static Optional<String> buzz(int x) {
        if (x % 5 == 0)
            return Optional.of("Buzz");
        else
            return Optional.empty();
    }
}
