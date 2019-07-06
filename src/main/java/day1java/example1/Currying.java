package day1java.example1;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

    static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> f) {
        throw new RuntimeException("TODO");
    }


    static <A, B, C, D> Function<A, Function<B, Function<C, D>>> curry(TriFunction<A, B, C, D> f) {
        throw new RuntimeException("TODO");
    }


    static <A, B, C, D, E> Function<A, Function<B, Function<C, Function<D, E>>>> curry(QuadFunction<A, B, C, D, E> f) {
        throw new RuntimeException("TODO");
    }

    static <A, B> B at(Function<A, B> f, A a) {
        throw new RuntimeException("TODO");
    }

}
