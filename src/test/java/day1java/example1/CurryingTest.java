package day1java.example1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static day1java.example1.Currying.at;
import static day1java.example1.Currying.curry;
import static org.assertj.core.api.Assertions.assertThat;

public class CurryingTest {

    static String strConcat(String s1, String s2) {
        return s1 + s2;
    }


    @Test
    public void curryingConcat() {

        Function<String, String> starPrefix = curry(CurryingTest::strConcat).apply("*");

        assertThat(starPrefix.apply("abc")).isEqualTo("*abc");

    }


    @Test
    public void curryingPerson() {

        Date now = new Date();
        Person fred = at(at(at(at(curry(Person::new), now), false), 4), "Fred");

        Person fredOrig = new Person(now, false, 4, "Fred");

        assertThat(fred).isEqualTo(fredOrig);

    }

    @Test
    public void creatingPeople() {

        Function<Integer, Function<String, Person>> personPartBuilder = at(at(curry(Person::new), new Date()), true);

        List<String> names = Arrays.asList("Fred", "Mary", "Ann", "Bob");

        AtomicInteger i = new AtomicInteger(1);
        List<Person> people = names.stream()
                .map(name -> personPartBuilder.apply(i.getAndIncrement()).apply(name))
                .collect(Collectors.toList());

        System.out.println(people);

        assertThat(people).hasSize(names.size());

    }

}
