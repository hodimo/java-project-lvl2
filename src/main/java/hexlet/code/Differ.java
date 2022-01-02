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
        return Formatter.format(
                genIntermediateDiff(path1, path2),
                formatName);
    }

    public static String generate(String path1, String path2) throws IOException {
        return Formatter.format(
                genIntermediateDiff(path1, path2),
                "stylish");
    }

    private static Map<String, Map<String, List<Object>>> genIntermediateDiff(
            String path1, String path2) throws IOException {
        Map<String, Object> data1 = getContentAsMap(path1);
        Map<String, Object> data2 = getContentAsMap(path2);

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

    private static Map<String, Object> getContentAsMap(String filePath) throws IOException {
        Path path = Path.of(filePath).toAbsolutePath().normalize();
        String content = Files.readString(path);
        return Parser.parse(content, filePath.endsWith(".json") ? "json" : "yaml");
    }
}
