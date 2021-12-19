package hexlet.code.Differ.Parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public abstract class Parser {
    public abstract Map<String, Object> unserializeToMap(String path) throws IOException;

    public final String serialize(Object value) throws IOException {
        return new ObjectMapper().writeValueAsString(value);
    }
}
