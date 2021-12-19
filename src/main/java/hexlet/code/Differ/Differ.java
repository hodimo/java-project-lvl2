package hexlet.code.Differ;

import hexlet.code.Differ.Factories.FormatterFactory;
import hexlet.code.Differ.Factories.ParserFactory;
import hexlet.code.Differ.Parser.Parser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Differ {
    public static String differ(String path1, String path2, String format) throws IOException {
        if (!ParserFactory.getParser(path1).getClass()
                .equals(ParserFactory.getParser(path2).getClass())) {
            throw new IOException("Please use file with the same type");
        }

        final Parser parser = ParserFactory.getParser(path1);
        Map<String, Object> data1 = parser.unserializeToMap(path1);
        Map<String, Object> data2 = parser.unserializeToMap(path2);
        Map<String, Object> differences = generate(data1, data2);

        return FormatterFactory.getFormatter(format)
                .format(differences, parser);
    }

    private static Map<String, Object> generate(Map<String, Object> data1, Map<String, Object> data2) {
        Map<String, Object> differences = new LinkedHashMap<>();
        Set<String> mergedKeys = new TreeSet<>(data1.keySet());
        mergedKeys.addAll(data2.keySet());
        for (String key : mergedKeys) {
            if (data1.containsKey(key) && !(data2.containsKey(key))) {
                differences.put("removed: " + key, data1.get(key));
            } else if (data2.containsKey(key) && !(data1.containsKey(key))) {
                differences.put("added: " + key, data2.get(key));
            } else if (((data1.get(key) == null && data2.get(key) != null)
                    || (data1.get(key) != null && data2.get(key) == null)
                    || !data1.get(key).equals(data2.get(key)))) {
                differences.put("removed: " + key, data1.get(key));
                differences.put("added: " + key, data2.get(key));
            } else {
                differences.put("unchanged: " + key, data2.get(key));
            }
        }
        return differences;
    }
}
