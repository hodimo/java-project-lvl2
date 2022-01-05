package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Formatter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(
            List<Map<String, Object>> diffs,
            String formatName) throws IOException {
        return switch (formatName) {
            case "stylish" -> StylishFormatter.format(diffs);
            case "plain" -> PlainFormatter.format(diffs);
            case "json" -> JsonFormatter.format(diffs);
            default -> throw new IOException("You should use one of these formats: stylish, plain, json");
        };
    }
}
