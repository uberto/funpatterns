package day1java.exercise2;

public class JsonFolder {

    static String compactJson(String json) {
        return json
                .chars()
                .mapToObj(c -> (char) c)
                .reduce( new JsonCompactorOutsideQuotes(""), JsonFolder::accumulate, JsonFolder::combine)
                .newJson();
    }

    private static JsonCompactor combine(JsonCompactor a, JsonCompactor b) {
        throw new RuntimeException("We don't support parallel compacting");
    }

    static JsonCompactor accumulate(JsonCompactor prev, char c) {
        return prev.compact(c);
    }

}