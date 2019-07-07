package day1java.exercise3;

import java.util.function.Function;
import java.util.function.Supplier;

public class EchoMachine {

    private final Function<String, Console<Void>> writer;
    private final Supplier<Console<String>> reader;

    public EchoMachine(Function<String, Console<Void>> writer, Supplier<Console<String>> reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void echo() {
        throw new RuntimeException("TODO");
    }
}
