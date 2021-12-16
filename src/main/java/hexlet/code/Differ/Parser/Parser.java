package hexlet.code.Differ.Parser;

import java.io.IOException;
import java.util.Map;

public abstract class Parser {
    public abstract Map<String, Object> unserializeToMap(String path) throws IOException;
    public abstract String serialize(Object value) throws IOException;
}
