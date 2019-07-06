package day1java.exercise2;


public class JsonCompactorOutsideQuotes implements JsonCompactor {

    private String newJson;

    public JsonCompactorOutsideQuotes(String newJson) {
        this.newJson = newJson;
    }

    @Override
    public String newJson() {
        return newJson;
    }

    @Override
    public JsonCompactor compact(char c) {
        if (c == '"')
            return new JsonCompactorInsideQuotes(newJson + c);
        else if (c == ' ' || c == '\n' || c == '\t')
            return this;
        else
            return new JsonCompactorOutsideQuotes(newJson + c);
    }
}
