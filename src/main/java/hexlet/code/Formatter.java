package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Formatter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(
            Map<String, Map<String, List<Object>>> differences,
            String formatName) throws IOException {
        switch (formatName) {
            case "stylish" -> {
                return stylishFormat(differences);
            }
            case "plain" -> {
                return plainFormat(differences);
            }
            case "json" -> {
                return jsonFormat(differences);
            }
            default -> throw new IOException("You should use one of these formats: stylish, plain, json");
        }
    }

    private static String stylishFormat(
            Map<String, Map<String, List<Object>>> differences) throws JsonProcessingException {
        StringBuilder diffs = new StringBuilder("{");
        for (String key: differences.keySet()) {
            String diffType = (String) differences.get(key).keySet().toArray()[0];
            List<Object> values = new ArrayList<>(differences.get(key).get(diffType));
            switch (diffType) {
                case "unchanged" -> diffs.append("\n    ");
                case "removed" -> diffs.append("\n  - ");
                case "added" -> diffs.append("\n  + ");
                default -> {
                    diffs.append("\n  - ").append(key).append(": ");
                    diffs.append(MAPPER.writeValueAsString(values.get(0)));
                    diffs.append("\n  + ").append(key).append(": ");
                    diffs.append(MAPPER.writeValueAsString(values.get(1)));
                }
            }
            if (!diffType.equals("updated")) {
                diffs.append(key).append(": ").append(MAPPER.writeValueAsString(values.get(0)));
            }
        }
        diffs.append("\n}");

        return diffs.toString()
                .replaceAll("(?<=\"):", "=")
                .replaceAll("\"(.*?)\"", "$1")
                .replaceAll(",", ", ");
    }

    private static String plainFormat(Map<String, Map<String, List<Object>>> differences) throws IOException {
        StringBuilder diffs = new StringBuilder();
        for (String key: differences.keySet()) {
            String diffType = (String) differences.get(key).keySet().toArray()[0];
            List<Object> values = new ArrayList<>(differences.get(key).get(diffType));
            switch (diffType) {
                case "removed" -> diffs.append(String.format(
                        "Property '%s' was removed%n",
                        key.replaceAll("\"", "")));
                case "added" -> diffs.append(String.format(
                        "Property '%s' was added with value: %s%n",
                        key.replaceAll("\"", ""),
                        processValueForPlain(MAPPER.writeValueAsString(values.get(0)))));
                case "updated" -> diffs.append(String.format(
                        "Property '%s' was updated. From %s to %s%n",
                        key.replaceAll("\"", ""),
                        processValueForPlain(MAPPER.writeValueAsString(values.get(0))),
                        processValueForPlain(MAPPER.writeValueAsString(values.get(1)))));
                default -> diffs.append("");
            }
        }
        return diffs.deleteCharAt(diffs.length() - 1).toString();
    }
    private static String processValueForPlain(String serializedValue) {
        if (serializedValue.matches("((\\{)|(\\[)).*((})|(]))")) {
            return "[complex value]";
        } else {
            return serializedValue.replaceAll("\"(.*)\"", "'$1'");
        }
    }

    private static String jsonFormat(Map<String, Map<String, List<Object>>> differences) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(differences);
    }
}
