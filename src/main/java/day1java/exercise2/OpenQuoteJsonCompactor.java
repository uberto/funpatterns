package day1java.exercise2;

public class OpenQuoteJsonCompactor implements JsonCompactor{

    private final String newJson;

    public OpenQuoteJsonCompactor(String newJson) {
        this.newJson = newJson;
    }

    @Override
    public String newJson() {
        return newJson;
    }

    @Override
    public JsonCompactor compact(char c) {
        String newJson = this.newJson + c;
        if(c == '"') {
            return new ClosedQuoteJsonCompactor(newJson);
        }
        return new OpenQuoteJsonCompactor(newJson);
    }
}
