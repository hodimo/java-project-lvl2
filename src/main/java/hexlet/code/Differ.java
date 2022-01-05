package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
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

    private static List<Map<String, Object>> genIntermediateDiff(
            Map<String, Object> data1, Map<String, Object> data2) {

        List<Map<String, Object>> diffs = new ArrayList<>();
        Set<String> mergedKeys = new TreeSet<>(data1.keySet());
        mergedKeys.addAll(data2.keySet());
        for (String key : mergedKeys) {
            Map<String, Object> map = new LinkedHashMap<>();
            if (!(data2.containsKey(key))) {
                map.put("status", "removed");
                map.put("fieldName", key);
                map.put("oldValue", data1.get(key));
            } else if (!(data1.containsKey(key))) {
                map.put("status", "added");
                map.put("fieldName", key);
                map.put("newValue", data2.get(key));
            } else if (!Objects.equals(data1.get(key), data2.get(key))) {
                map.put("status", "updated");
                map.put("fieldName", key);
                map.put("oldValue", data1.get(key));
                map.put("newValue", data2.get(key));
            } else {
                map.put("status", "unchanged");
                map.put("fieldName", key);
                map.put("oldValue", data1.get(key));
                map.put("newValue", data2.get(key));
            }
            diffs.add(map);
        }
        return diffs;
    }
}
