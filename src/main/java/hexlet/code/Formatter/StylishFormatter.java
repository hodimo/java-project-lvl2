package hexlet.code.Formatter;

import hexlet.code.Parser.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StylishFormatter extends Formatter {
    @Override
    public final String format(Map<String, List<Object>> differences, Parser parser) throws IOException {
        StringBuilder diffs = new StringBuilder("{");
        for (String key: differences.keySet()) {
            String label = (String) differences.get(key).get(0);
            switch (label) {
                case "unchanged" -> diffs.append("\n    ").append(key).append(": ");
                case "removed" -> diffs.append("\n  - ").append(key).append(": ");
                case "added" -> diffs.append("\n  + ").append(key).append(": ");
                case "updated" -> {
                    diffs.append("\n  - ").append(key).append(": ");
                    diffs.append(parser.serialize(differences.get(key).get(1)));
                    diffs.append("\n  + ").append(key).append(": ");
                    diffs.append(parser.serialize(differences.get(key).get(2)));
                }
                default -> diffs.append("");
            }
            if (!label.equals("updated")) {
                diffs.append(parser.serialize(differences.get(key).get(1)));
            }
        }
        diffs.append("\n}");

        return diffs.toString()
                .replaceAll("\"", "")
                .replaceAll(",\\n", "\n")
                .replaceAll("((( {4})|( {2}- )|( {2}\\+ )).+:) \\n", "$1\n")
                .replaceAll(",", ", ");
    }
}
