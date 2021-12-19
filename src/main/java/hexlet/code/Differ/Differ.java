package hexlet.code.Differ;

import hexlet.code.Differ.Factories.ParserFactory;
import hexlet.code.Differ.Parser.Parser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.SortedMap;
import java.util.TreeMap;


public class Differ {
    private static final int DIFF_PREFIX_END = 3;

    public static String generate(String path1, String path2) throws IOException {
        if (!ParserFactory.getParser(path1).getClass()
                .equals(ParserFactory.getParser(path2).getClass())) {
            throw new IOException("Please use file with the same type");
        }

        final Parser parser = ParserFactory.getParser(path1);

        Map<String, Object> data1 = parser.unserializeToMap(path1);
        Map<String, Object> data2 = parser.unserializeToMap(path2);

        Map<String, Object> differences = new WeakHashMap<>(); //can't work with null values on other maps
        Set<String> mergedKeys = new TreeSet<>(data1.keySet());
        mergedKeys.addAll(data2.keySet());
        for (String key : mergedKeys) {
            if (data1.containsKey(key) && !(data2.containsKey(key))) {
                differences.put(" - " + key, data1.get(key));
            } else if (data2.containsKey(key) && !(data1.containsKey(key))) {
                differences.put(" + " + key, data2.get(key));
            } else if (data1.get(key).equals(data2.get(key))) {
                differences.put("   " + key, data2.get(key));
            } else {
                differences.put(" - " + key, data1.get(key));
                differences.put(" + " + key, data2.get(key));
            }
        }
        SortedMap<String, Object> sortedDifferences = new TreeMap<>((key1, key2) ->  {
            if (key1.substring(DIFF_PREFIX_END).equals(key2.substring(DIFF_PREFIX_END))) {
                return key1.charAt(1) == '-' ? -1 : 1;
            }
            return key1.substring(DIFF_PREFIX_END).compareTo(key2.substring(DIFF_PREFIX_END));
        });
        sortedDifferences.putAll(differences);

        return formatDiffs(sortedDifferences, parser);
    }

    private static String formatDiffs(Map<String, Object> sortedDifferences, Parser parser) throws IOException {
        if (parser.getClass().getName().contains("Json")) {
            return parser.serialize(sortedDifferences)
                    .replaceAll("\"", "")
                    .replaceAll("\\s\\+\\s", "\n + ")
                    .replaceAll("\\s-\\s", "\n - ")
                    .replaceAll("\\s{3}", "\n   ")
                    .replaceAll(",\n", "\n")
                    .replaceAll("}$", "\n}")
                    .replaceAll("(:(.*)\n)", ": $2\n")
                    .replaceAll(": \n", ":\n");
        } else if (parser.getClass().getName().contains("Yaml")) {
            String str = parser.serialize(sortedDifferences)
                    .replaceAll("^-{3}", "{")
                    .replaceAll("'", "")
                    .replaceAll("\"", "") + "}"
                    .replaceAll("(:(.*)\n)", ": $2\n");
            return str.replaceAll(": \n", ":\n");
        }
        return "You need to think more.";
    }
}
