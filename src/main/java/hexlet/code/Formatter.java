package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Formatter {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String format(Map<String, List<Object>> differences, String formatName) throws IOException {
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

    private static String stylishFormat(Map<String, List<Object>> differences) throws JsonProcessingException {
        StringBuilder diffs = new StringBuilder("{");
        for (String key: differences.keySet()) {
            String label = (String) differences.get(key).get(0);
            switch (label) {
                case "unchanged" -> diffs.append("\n    ").append(key).append(": ");
                case "removed" -> diffs.append("\n  - ").append(key).append(": ");
                case "added" -> diffs.append("\n  + ").append(key).append(": ");
                case "updated" -> {
                    diffs.append("\n  - ").append(key).append(": ");
                    diffs.append(MAPPER.writeValueAsString(differences.get(key).get(1)));
                    diffs.append("\n  + ").append(key).append(": ");
                    diffs.append(MAPPER.writeValueAsString(differences.get(key).get(2)));
                }
                default -> diffs.append("");
            }
            if (!label.equals("updated")) {
                diffs.append(MAPPER.writeValueAsString(differences.get(key).get(1)));
            }
        }
        diffs.append("\n}");

        return diffs.toString()
                .replaceAll("(\\\"):(\\\"|(.+))", "$1=$1$3")
                .replaceAll("\"", "")
                .replaceAll(",\\n", "\n")
                .replaceAll("((( {4})|( {2}- )|( {2}\\+ )).+:) \\n", "$1\n")
                .replaceAll(",", ", ");
    }

    private static String plainFormat(Map<String, List<Object>> differences) throws IOException {
        StringBuilder diffs = new StringBuilder();
        for (String key: differences.keySet()) {
            String label = (String) differences.get(key).get(0);
            switch (label) {
                case "removed" -> diffs.append(String.format(
                        "Property '%s' was removed%n",
                        key.replaceAll("\"", "")));
                case "added" -> diffs.append(String.format(
                        "Property '%s' was added with value: %s%n",
                        key.replaceAll("\"", ""),
                        processValueForPlain(MAPPER.writeValueAsString(differences.get(key).get(1)))));
                case "updated" -> diffs.append(String.format(
                        "Property '%s' was updated. From %s to %s%n",
                        key.replaceAll("\"", ""),
                        processValueForPlain(MAPPER.writeValueAsString(differences.get(key).get(1))),
                        processValueForPlain(MAPPER.writeValueAsString(differences.get(key).get(2)))));
                default -> diffs.append("");
            }
        }
        return diffs.deleteCharAt(diffs.length() - 1).toString();
    }
    private static String processValueForPlain(String serializedValue) {
        if (serializedValue.matches("((\\{)|(\\[)).+((})|(]))")) {
            return "[complex value]";
        }
        return serializedValue.matches("\".+\"")
                ? serializedValue.replaceAll("\"", "'")
                : serializedValue;
    }

    private static String jsonFormat(Map<String, List<Object>> differences) throws IOException {
        Map<String, Map<String, Object>> diffs = new LinkedHashMap<>();
        for (String key : differences.keySet()) {
            Map<String, Object> values = new LinkedHashMap<>();
            String label = (String) differences.get(key).get(0);
            if ("removed".equals(label)) {
                values.put("oldValue", differences.get(key).get(1));
                values.put("newValue", null);
            } else if ("added".equals(label)) {
                values.put("oldValue", null);
                values.put("newValue", differences.get(key).get(1));
            } else if ("updated".equals(label)) {
                values.put("oldValue", differences.get(key).get(1));
                values.put("newValue", differences.get(key).get(2));
            } else {
                values.put("oldValue", differences.get(key).get(1));
                values.put("newValue", differences.get(key).get(1));
            }
            diffs.put(key, values);
        }

        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(diffs);
    }
}
