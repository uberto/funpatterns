package day1java.example2;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTest {

    @Test
    public void fizzBuzzReturnsListOfStringsAccordingToTheRules() {

        String res = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
                .map(FizzBuzz::fizzBuzz)
                .collect(Collectors.joining(", "));

        String expected = "1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz";

        assertThat(res).isEqualTo(expected);

    }
}
