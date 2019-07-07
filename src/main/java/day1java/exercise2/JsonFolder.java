package day1java.exercise2;

public class JsonFolder {

    static String compactJson(String json) {
        return json
                .chars()
                .mapToObj(c -> (char) c)
                .reduce(new ClosedQuoteJsonCompactor(""), JsonFolder::accumulate, JsonFolder::combine)
                .newJson();
    }

    private static JsonCompactor combine(JsonCompactor a, JsonCompactor b) {
        if (a.newJson().length() < b.newJson().length())
            return b;
        else
            return a;
    }

    private static JsonCompactor accumulate(JsonCompactor prev, char c) {
        return prev.compact(c);
    }

}
