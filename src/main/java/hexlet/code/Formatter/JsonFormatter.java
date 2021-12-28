package hexlet.code.Formatter;

import hexlet.code.Parser.Parser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter extends Formatter {
    @Override
    public final String format(Map<String, List<Object>> differences, Parser parser) throws IOException {
        Map<String, Map<String, Object>> diffs = new LinkedHashMap<>();
        for (String key : differences.keySet()) {
            Map<String, Object> values = new LinkedHashMap<>();
            String label = (String) differences.get(key).get(0);
            if ("removed".equals(label)) {
                values.put("oldValue", differences.get(key).get(1));
                values.put("newValue", null);
            } else if ("added".equals(label)) {
                values.put("oldValue", null);
                values.put("newValue", differences.get(key).get(1));
            } else if ("updated".equals(label)) {
                values.put("oldValue", differences.get(key).get(1));
                values.put("newValue", differences.get(key).get(2));
            } else {
                values.put("oldValue", differences.get(key).get(1));
                values.put("newValue", differences.get(key).get(1));
            }
            diffs.put(key, values);
        }

        return parser.serialize(diffs);
    }
}
