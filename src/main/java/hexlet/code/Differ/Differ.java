package hexlet.code.Differ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Differ {
    final static ObjectMapper mapper = new ObjectMapper();

    public static String generate( String path1, String path2) throws IOException {
        Map<String, Object> data1 = new WeakHashMap<>();
        Map<String, Object> data2 = new WeakHashMap<>();
        assert path1 != null;
        if (path1.endsWith(".json") && path2.endsWith(".json")) {
            data1 = mapper.readValue(Files.readString(Paths.get(path1)),
                    new TypeReference<>() {});
            data2 = mapper.readValue(Files.readString(Paths.get(path2)),
                    new TypeReference<>() {});
        }

        Map<String, Object> differences = new WeakHashMap<>();
        Set<String> mergedKeys = new TreeSet<>(data1.keySet());
        mergedKeys.addAll(data2.keySet());
        for(String key : mergedKeys) {
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
            if (key1.substring(3).equals(key2.substring(3))) {
                return key1.charAt(1) == '-' ? -1 : 1;
            }
            return key1.substring(3).compareTo(key2.substring(3));
        });
        sortedDifferences.putAll(differences);

        return formatDiffs(mapper.writeValueAsString(sortedDifferences));
    }

    private static String formatDiffs(String diffs) {
        StringBuilder str = new StringBuilder("{\n");
        String[] fieldArr = diffs.split("\\s\\W\\s");
        for (String field : fieldArr) {
            str.append(field).append("\n");
        }
        return diffs;
    }
}