package day1java.example3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Outcome<ERR, T> {

    default <U> Outcome<ERR, U> map(Function<T, U> f) {
        if (this instanceof Success) {
            return new Success(f.apply(((Success<T>) this).value));
        } else if (this instanceof Failure) {
            return ((Failure) this);
        } else {
            throw new RuntimeException("Unknown type " + this.getClass().getSimpleName());
        }
    }


    default <ERR2> Outcome<ERR2, T> mapFailure(Function<ERR, ERR2> f) {
        if (this instanceof Success) {
            return ((Success) this);
        } else if (this instanceof Failure) {
            return new Failure(f.apply(((Failure<ERR>) this).error));
        } else {
            throw new RuntimeException("Unknown type " + this.getClass().getSimpleName());
        }
    }


    class Success<T> implements Outcome<Void, T> {
        public final T value;

        public Success(T value) {
            this.value = value;
        }
    }

    class Failure<ERR> implements Outcome<ERR, Void> {
        public final ERR error;

        public Failure(ERR error) {
            this.error = error;
        }
    }

    static <T> Outcome<Throwable, T> tryThis(Supplier<T> block) {
        try {
            return new Success(block.get());
        } catch (Throwable e) {
            return new Failure(e);
        }
    }


    default <U> Outcome<ERR, U> flatMap(Function<T, Outcome<ERR, U>> f) {
        if (this instanceof Success) {
            return f.apply(((Success<T>) this).value);
        } else if (this instanceof Failure) {
            return ((Failure) this);
        } else {
            throw new RuntimeException("Unknown type " + this.getClass().getSimpleName());
        }
    }


    default T onFailure(Consumer<ERR> block) {
        if (this instanceof Success) {
            return ((Success<T>) this).value;
        } else if (this instanceof Failure) {
            block.accept(((Failure<ERR>) this).error);
            throw new RuntimeException("Unmanged failure");
        } else {
            throw new RuntimeException("Unknown type " + this.getClass().getSimpleName());
        }
    }

}
