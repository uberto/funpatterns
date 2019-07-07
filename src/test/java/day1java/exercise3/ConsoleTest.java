package day1java.exercise3;

import org.junit.jupiter.api.Test;

public class ConsoleTest {

    //implement pseudoPrint and pseudoReadline using Queue<String> instead than stdin and stdout

    static Console<Void> pseudoPrint(String msg) { throw new RuntimeException("TODO"); }
    static Console<String> pseudoReadline() { throw new RuntimeException("TODO"); }

    @Test
    public void howCanWeTestTheConsole(){

        EchoMachine echo = new EchoMachine(ConsoleTest::pseudoPrint, ConsoleTest::pseudoReadline);

        echo.echo();

        throw new RuntimeException("finish the test with assertions");

    }


    @Test
    public void testAMiniCmdlineCalculator(){
        // giving + 1 1
        // should give 2 as result

    }

}