package day1java.exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

public class Console<T> {

    public Console(Supplier<T> exec){
        this.exec = exec;
    }

    public Supplier<T> exec;


    public static Console<Void> printIO(String msg) {
        return new Console<>(() -> {
            System.out.println(msg);
            return null;
        });
    }

    public static Console<String> readlineIO() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return new Console<String>(() -> {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
