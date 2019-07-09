package day1java.exercise3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static day1java.exercise3.Unit.unit;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleTest {

    private static List<String> printLines = new LinkedList<>();
    private static List<String> inputs = new LinkedList<>();

    //implement pseudoPrint and pseudoReadline using Queue<String> instead than stdin and stdout

    static Console<Unit> pseudoPrint(String msg) {
        return new Console<>(() -> {
            printLines.add(msg);
            return unit;
        });
    }

    static Console<String> pseudoReadline() {
        return new Console<>(() -> inputs.remove(0));
    }

    @AfterEach
    void tearDown() {
        inputs.clear();
        printLines.clear();
    }

    @Test
    public void howCanWeTestTheConsole() {

        inputs.add("Hi there :D");

        EchoMachine echo = new EchoMachine(ConsoleTest::pseudoPrint, ConsoleTest::pseudoReadline);

        echo.echo();

        assertThat(printLines).containsOnly("Hi there :D");
    }


    @Test
    public void testAMiniCmdlineCalculatorAddition() {
        inputs.add("+ 1 1");

        CommandLineCalculator commandLineCalculator = new CommandLineCalculator(
            ConsoleTest::pseudoPrint,
            ConsoleTest::pseudoReadline
        );

        commandLineCalculator.run();

        assertThat(printLines).containsOnly("2");
    }

    @Test
    public void testAMiniCmdlineCalculatorMultiplication() {
        inputs.add("* 1 3");

        CommandLineCalculator commandLineCalculator = new CommandLineCalculator(
                ConsoleTest::pseudoPrint,
                ConsoleTest::pseudoReadline
        );

        commandLineCalculator.run();

        assertThat(printLines).containsOnly("3");
    }
}