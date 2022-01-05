package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class StylishFormatter {
    public static String format(List<Map<String, Object>> diffs) {
        StringBuilder sb = new StringBuilder("{");
        for (Map<String, Object> diff: diffs) {
            String fieldName = (String) diff.get("fieldName");
            switch ((String) diff.get("status")) {
                case "removed" -> sb.append("\n  - ").append(fieldName).append(": ").append(diff.get("oldValue"));
                case "added" -> sb.append("\n  + ").append(fieldName).append(": ").append(diff.get("newValue"));
                case "unchanged" -> sb.append("\n    ").append(fieldName).append(": ").append(diff.get("oldValue"));
                default -> {
                    sb.append("\n  - ").append(fieldName).append(": ").append(diff.get("oldValue"));
                    sb.append("\n  + ").append(fieldName).append(": ").append(diff.get("newValue"));
                }
            }
        }
        sb.append("\n}");

        return sb.toString();
    }
}
