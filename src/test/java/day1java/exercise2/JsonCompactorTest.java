package day1java.exercise2;

import org.junit.jupiter.api.Test;

import static day1java.exercise2.JsonFolder.compactJson;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonCompactorTest {


    @Test
    public void stripSpaces() {

        String jsonText = "{ \"a\" : 20 }";

        String expected = "{\"a\":20}";

        assertThat(compactJson(jsonText)).isEqualTo(expected);

    }


    @Test
    public void stripNewLinesAndTabs() {

        String jsonText = "{ \t\t\"a\" : 20, \n \"b\": true }";

        String expected = "{\"a\":20,\"b\":true}";

        assertThat(compactJson(jsonText)).isEqualTo(expected);

    }


    @Test
    public void keepSpacesInQuotes() {

        String jsonText = "{ \"my greetings\" :   \"hello world\" }";

        String expected = "{\"my greetings\":\"hello world\"}";

        assertThat(compactJson(jsonText)).isEqualTo(expected);

    }


}
