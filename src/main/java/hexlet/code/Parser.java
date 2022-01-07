package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    public static Map<String, Object> parse(String content, String fileExtension) throws JsonProcessingException {
        ObjectMapper mapper;
        if (fileExtension.equals("json")) {
            mapper = new ObjectMapper();
        } else if (fileExtension.equals("yaml") || fileExtension.equals("yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            throw new RuntimeException("Cannot parse file with extension: " + fileExtension);
        }
        return mapper.readValue(content, new TypeReference<>() { });
    }
}
