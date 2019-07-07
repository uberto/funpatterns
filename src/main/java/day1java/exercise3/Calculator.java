package day1java.exercise3;

import java.util.function.Function;
import java.util.function.Supplier;

public class Calculator {

    private final Function<String, Console<Void>> writer;
    private final Supplier<Console<String>> reader;

    public Calculator(Function<String, Console<Void>> writer, Supplier<Console<String>> reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void calc() {
        String cmd = reader.get().exec.get();

        String[] elements = cmd.split(" ");

        String res;
        if (elements.length != 3) {
            res = "Syntax Error " + cmd;
        } else {
            res = operation(elements[0], elements[1], elements[2]);
        }
        writer.apply(res).exec.get();
    }

    private String operation(String op, String a, String b) {
        double x = Double.parseDouble(a);
        double y = Double.parseDouble(b);

        switch (op) {
            case "+":
                return String.valueOf(x + y);
            case "-":
                return String.valueOf(x - y);
            case "*":
                return String.valueOf(x * y);
            case "/":
                return String.valueOf(x / y);
            default:
                return "Unknown operation $op";
        }
    }

}



