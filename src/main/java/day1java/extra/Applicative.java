package day1java.extra;

import java.util.function.Function;

public class Applicative<T> {

    public final T value;

    public Applicative(T value) {
        this.value = value;
    }

    <R> Applicative<R> applyOn(Applicative<Function<T, R>> other) {
        return new Applicative<>(other.value.apply(value));
    }
}
