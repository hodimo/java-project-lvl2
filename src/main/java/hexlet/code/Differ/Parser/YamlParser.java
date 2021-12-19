package hexlet.code.Differ.Parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class YamlParser extends Parser {
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @Override
    public final Map<String, Object> unserializeToMap(String pathFile) {
        Path path = Path.of(pathFile).toAbsolutePath().normalize();
        Map<String, Object> yamlAsMap = new HashMap<>();

        try {
            yamlAsMap = MAPPER.readValue(Files.readString(
                    path), new TypeReference<>() { });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return yamlAsMap;
    }
}