package day1java.exercise3;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleTest {

    static Deque<String> output = new ArrayDeque<>();
    static Deque<String> input = new ArrayDeque<>();

    static Console<Void> pseudoPrint(String msg) {
        return new Console <> (() -> {
            output.add(msg);
            return null;
        });
    }

    static Console<String> pseudoReadline() {
        return new Console <> (() -> input.remove());
    }

    @Test
    public void howCanWeTestTheConsole() {

        EchoMachine echo = new EchoMachine(ConsoleTest::pseudoPrint, ConsoleTest::pseudoReadline);

        input.add("Hello");
        input.add("Functional");
        input.add("World");

        echo.echo();
        echo.echo();
        echo.echo();

        assertThat(output.remove()).isEqualTo("Hello");
        assertThat(output.remove()).isEqualTo("Functional");
        assertThat(output.remove()).isEqualTo("World");

    }


    @Test
    public void testAMiniCmdlineCalculator() {
        // giving + 1 1
        // should give 2 as result

        Calculator calc = new Calculator(ConsoleTest::pseudoPrint, ConsoleTest::pseudoReadline);

        input.add("+ 4 5");
        input.add("/ 21 7");
        input.add("* 123 987");

        calc.calc();
        calc.calc();
        calc.calc();

        assertThat(output.remove()).isEqualTo("9.0");
        assertThat(output.remove()).isEqualTo("3.0");
        assertThat(output.remove()).isEqualTo("121401.0");

    }

}