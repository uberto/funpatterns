package day1java.exercise3;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class CommandLineCalculator{

    private final Function<String, Console<Unit>> writer;
    private final Supplier<Console<String>> reader;

    public CommandLineCalculator(Function<String, Console<Unit>> writer, Supplier<Console<String>> reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void run() {
        String input = reader.get().exec.get();
        String[] parts = input.split(" ");
        String operator = parts[0];
        Integer operand1 = Integer.valueOf(parts[1]);
        Integer operand2 = Integer.valueOf(parts[2]);
        int result = doTheMath(operator, operand1, operand2);
        writer.apply(String.valueOf(result)).exec.get();
    }

    private int doTheMath(String operator, Integer operand1, Integer operand2) {
        switch (operator){
            case "+":
                return addition.apply(operand1, operand2);
            case "*":
                return multiplication.apply(operand1, operand2);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private BiFunction<Integer, Integer, Integer> addition = (a, b) -> a + b;
    private BiFunction<Integer, Integer, Integer> multiplication = (a, b) -> a * b;

}
