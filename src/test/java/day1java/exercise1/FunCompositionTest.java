package day1java.exercise1;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static day1java.exercise1.FunComposition.andThen;
import static day1java.exercise1.FunComposition.on;
import static org.assertj.core.api.Assertions.assertThat;

public class FunCompositionTest {

    static int inc(int x) {
        return x + 1;
    }

    static int doubleIt(int x) {
        return x * 2;
    }

    static String pad(String s) {
        return " " + s;
    }

    static String mirror(String s) {
        return s + new StringBuilder(s).reverse().toString();
    }

    static int strLen(String s) {
        return s.length();
    }


    @Test
    public void fOnG() {
// f on g  is equivalent to f(g())

        int r = inc(doubleIt(5)); //11

        Function<Integer, Integer> newFun = on(FunCompositionTest::inc, FunCompositionTest::doubleIt);

        assertThat(newFun.apply(5)).isEqualTo(r);

    }

    @Test
    public void fOnGDifferentTypes() {
//f on g with different types  is equivalent to f(g())

        int r = inc(strLen("ciao")); //5

        Function<String, Integer> newFun = on(FunCompositionTest::inc, FunCompositionTest::strLen);

        assertThat(newFun.apply("ciao")).isEqualTo(r);

    }


    @Test
    public void fandThenG() {
//f andThen g  is equivalent to g(f())

        String r = mirror(pad("*")); //" ** "

        Function<String, String> newFun = andThen(FunCompositionTest::pad, FunCompositionTest::mirror);

        assertThat(newFun.apply("*")).isEqualTo(r);

    }

}
