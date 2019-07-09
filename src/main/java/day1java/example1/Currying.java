package day1java.example1;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {

    static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> f) {
        return a -> b -> f.apply(a, b);
    }


    static <A, B, C, D> Function<A, Function<B, Function<C, D>>> curry(TriFunction<A, B, C, D> f) {
        return a -> b -> c -> f.apply(a, b, c);
    }


    static <A, B, C, D, E> Function<A, Function<B, Function<C, Function<D, E>>>> curry(QuadFunction<A, B, C, D, E> f) {
        return a -> b -> c -> d -> f.apply(a, b, c, d);
    }

    static <A, B> B at(Function<A, B> f, A a) {
        return f.apply(a);
    }

}
