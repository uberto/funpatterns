package day1java.exercise2;


public class JsonCompactorInsideQuotes implements JsonCompactor {

    private final String newJson;

    public JsonCompactorInsideQuotes(String newJson) {
        this.newJson = newJson;
    }

    @Override
    public String newJson() {
        return newJson;
    }

    @Override
    public JsonCompactor compact(char c) {
        if (c == '"')
            return new JsonCompactorOutsideQuotes(newJson + c);
        else
            return new JsonCompactorInsideQuotes(newJson + c);
    }
}