package hexlet.code.formatters;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PlainFormatter {
    public static String format(List<Map<String, Object>> diffs) {
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> diff: diffs) {
            String fieldName = (String) diff.get("fieldName");
            switch ((String) diff.get("status")) {
                case "removed" -> sb.append(String.format("Property '%s' was removed%n", fieldName));
                case "added" -> sb.append(String.format("Property '%s' was added with value: %s%n", fieldName,
                        processValue(diff.get("newValue"))));
                case "updated" -> sb.append(String.format("Property '%s' was updated. From %s to %s%n", fieldName,
                        processValue(diff.get("oldValue")),
                        processValue(diff.get("newValue"))));
                default -> sb.append("");
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static String processValue(Object value) {
        if (value instanceof Object[]
                || value instanceof Collection
                || value instanceof Map) {
            return "[complex value]";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else {
            return String.valueOf(value);
        }
    }
}
