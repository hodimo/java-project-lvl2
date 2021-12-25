package hexlet.code.Differ.Formatter;

import hexlet.code.Differ.Parser.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PlainFormatter extends Formatter {
    @Override
    public final String format(Map<String, List<Object>> differences, Parser parser) throws IOException {
        StringBuilder diffs = new StringBuilder("");
        for (String key: differences.keySet()) {
            String label = (String) differences.get(key).get(0);
            switch (label) {
                case "removed" -> diffs.append(String.format(
                        "Property '%s' was removed%n",
                        key.replaceAll("\"", "")));
                case "added" -> diffs.append(String.format(
                        "Property '%s' was added with value: %s%n",
                        key.replaceAll("\"", ""),
                        processValue(parser.serialize(differences.get(key).get(1)))));
                case "updated" -> {
                    diffs.append(String.format("Property '%s' was updated. From %s to %s%n",
                            key.replaceAll("\"", ""),
                            processValue(parser.serialize(differences.get(key).get(1))),
                            processValue(parser.serialize(differences.get(key).get(2)))));
                }
                default -> diffs.append("");
            }
        }
        return diffs.deleteCharAt(diffs.length() - 1).toString();
    }

    private String processValue(String serializedValue) {
        if (serializedValue.matches("((\\{)|(\\[)).+((})|(]))")) {
            return "[complex value]";
        }
        return serializedValue.matches("\".+\"")
                ? serializedValue.replaceAll("\"", "'")
                : serializedValue;
    }
}
