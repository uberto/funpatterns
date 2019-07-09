package day1java.exercise3;

import java.util.function.Function;
import java.util.function.Supplier;

public class EchoMachine {

    private final Function<String, Console<Unit>> writer;
    private final Supplier<Console<String>> reader;

    public EchoMachine(Function<String, Console<Unit>> writer, Supplier<Console<String>> reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void echo() {
        Console<String> stringConsole = reader.get();

        String s = stringConsole.exec.get();

        Console<Unit> apply = writer.apply(s);

        apply.exec.get();
    }
}
