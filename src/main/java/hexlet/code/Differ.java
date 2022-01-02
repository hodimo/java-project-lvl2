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
    public static String generate(String pathAsStr1, String pathAsStr2, String formatName) throws IOException {
        Path path1 = Path.of(pathAsStr1).toAbsolutePath().normalize();
        String content1 = Files.readString(path1);
        Map<String, Object> data1 = Parser.parse(content1, pathAsStr1.endsWith(".json") ? "json" : "yaml");

        Path path2 = Path.of(pathAsStr2).toAbsolutePath().normalize();
        String content2 = Files.readString(path2);
        Map<String, Object> data2 = Parser.parse(content2, pathAsStr2.endsWith(".json") ? "json" : "yaml");
        return Formatter.format(
                genIntermediateDiff(data1, data2),
                formatName);
    }

    public static String generate(String pathAsStr1, String pathAsStr2) throws IOException {
        return generate(pathAsStr1, pathAsStr2, "stylish");
    }

    private static Map<String, Map<String, List<Object>>> genIntermediateDiff(
            Map<String, Object> data1, Map<String, Object> data2) throws IOException {

        Map<String, Map<String, List<Object>>> diffs = new LinkedHashMap<>();
        Set<String> mergedKeys = new TreeSet<>(data1.keySet());
        mergedKeys.addAll(data2.keySet());
        for (String key : mergedKeys) {
            List<Object> values = new ArrayList<>();
            if (data1.containsKey(key) && !(data2.containsKey(key))) {
                values.add(data1.get(key));
                diffs.put(key, Map.of("removed", new ArrayList<>(values)));
            } else if (data2.containsKey(key) && !(data1.containsKey(key))) {
                values.add(data2.get(key));
                diffs.put(key, Map.of("added", new ArrayList<>(values)));
            } else if (((data1.get(key) == null && data2.get(key) != null)
                    || (data1.get(key) != null && data2.get(key) == null)
                    || !data1.get(key).equals(data2.get(key)))) {
                values.add(data1.get(key));
                values.add(data2.get(key));
                diffs.put(key, Map.of("updated", new ArrayList<>(values)));
            } else {
                values.add(data2.get(key));
                diffs.put(key, Map.of("unchanged", new ArrayList<>(values)));
            }
        }
        return diffs;
    }
}
