package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import java.util.List;
import java.util.Map;

public class Formatter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(
            List<Map<String, Object>> diffs,
            String formatName) throws IllegalArgumentException, JsonProcessingException {
        return switch (formatName) {
            case "stylish" -> StylishFormatter.format(diffs);
            case "plain" -> PlainFormatter.format(diffs);
            case "json" -> JsonFormatter.format(diffs);
            default -> throw new IllegalArgumentException("You should use one of these formats: stylish, plain, json");
        };
    }
}
