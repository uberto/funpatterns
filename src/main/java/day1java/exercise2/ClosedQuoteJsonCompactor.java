package day1java.exercise2;

import java.util.Arrays;
import java.util.List;

public class ClosedQuoteJsonCompactor implements JsonCompactor {

    private static final List<Character> CHARS_TO_REMOVE = Arrays.asList('\t','\n',' ');
    private final String newJson;

    public ClosedQuoteJsonCompactor(String newJson) {
        this.newJson = newJson;
    }

    @Override
    public String newJson() {
        return newJson;
    }

    @Override
    public JsonCompactor compact(char c) {
        if(c == '"'){
            return new OpenQuoteJsonCompactor(newJson + c);
        }
        return CHARS_TO_REMOVE.contains(c)
                ? this
                : new ClosedQuoteJsonCompactor(newJson + c);
    }
}
