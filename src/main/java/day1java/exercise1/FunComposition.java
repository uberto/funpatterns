package day1java.exercise1;

import java.util.function.Function;

public class FunComposition {

    static <A,B,C> Function<A,C> on( Function<B,C> left, Function<A,B> right) {
        throw new RuntimeException("TODO");
    }

    static <A,B,C> Function<A,C> andThen( Function<A,B> left, Function<B,C> right)
    {
        throw new RuntimeException("TODO");
    }
}
