package hexlet.code.Differ.Parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonParser extends Parser {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public final Map<String, Object> unserializeToMap(String pathFile) throws IOException {
        Path path = Path.of(pathFile).toAbsolutePath().normalize();
        Map<String, Object> jsonAsMap = new HashMap<>();

        try {
            jsonAsMap = MAPPER.readValue(Files.readString(
                    path), new TypeReference<>() { });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonAsMap;
    }
}
