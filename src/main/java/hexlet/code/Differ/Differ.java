package hexlet.code.Differ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Differ {
    final static ObjectMapper mapper = new ObjectMapper();

    public static String generate( String path1, String path2) throws IOException {
        Map<String, Object> data1 = unserialize(path1);
        Map<String, Object> data2 = unserialize(path2);

        Map<String, Object> differences = new WeakHashMap<>(); //can't work with null values on other maps
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

        return formatDiffs(sortedDifferences);
    }

    private static String formatDiffs(Map<String, Object> diffs) throws IOException {
        StringBuilder str = new StringBuilder("{");
        for (Map.Entry<String, Object> kvPair: diffs.entrySet()) {
            String value = "";
            value = mapper.writeValueAsString(kvPair.getValue());
            str.append("\n")
                    .append(kvPair.getKey())
                    .append(": ")
                    .append(value.replaceAll("\"", ""));
        }
        return str.append("\n}").toString();
    }

    private static Map<String, Object> unserialize(String strPath) throws IOException {
        Path path = Path.of(strPath).toRealPath().normalize();
        Map<String, Object> jsonAsMap = new HashMap<>();

        try {
            jsonAsMap = mapper.readValue(Files.readString(
                    path), new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonAsMap;
    }
}