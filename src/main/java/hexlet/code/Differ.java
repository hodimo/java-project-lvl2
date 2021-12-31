package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;


public class Differ {
    public static String generate(String path1, String path2, String formatName) throws IOException {
        Path filePath1 = Path.of(path1).toAbsolutePath().normalize();
        Path filePath2 = Path.of(path2).toAbsolutePath().normalize();

        Map<String, Object> data1 = Parser.parse(
                Files.readString(filePath1),
                path1.endsWith(".json") ? "json" : "yaml");
        Map<String, Object> data2 = Parser.parse(
                Files.readString(filePath2),
                path2.endsWith(".json") ? "json" : "yaml");

        Map<String, List<Object>> differences = new LinkedHashMap<>();
        Set<String> mergedKeys = new TreeSet<>(data1.keySet());
        mergedKeys.addAll(data2.keySet());
        for (String key : mergedKeys) {
            List<Object> values = new ArrayList<>();
            if (data1.containsKey(key) && !(data2.containsKey(key))) {
                values.add("removed");
                values.add(data1.get(key));
                differences.put(key, new ArrayList<>(values));
            } else if (data2.containsKey(key) && !(data1.containsKey(key))) {
                values.add("added");
                values.add(data2.get(key));
                differences.put(key, new ArrayList<>(values));
            } else if (((data1.get(key) == null && data2.get(key) != null)
                    || (data1.get(key) != null && data2.get(key) == null)
                    || !data1.get(key).equals(data2.get(key)))) {
                values.add("updated");
                values.add(data1.get(key));
                values.add(data2.get(key));
                differences.put(key, new ArrayList<>(values));
            } else {
                values.add("unchanged");
                values.add(data2.get(key));
                differences.put(key, new ArrayList<>(values));
            }
            values.clear();
        }
        return Formatter.format(differences, formatName);
    }
}
