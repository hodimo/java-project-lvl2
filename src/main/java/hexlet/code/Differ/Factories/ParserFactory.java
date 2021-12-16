package hexlet.code.Differ.Factories;

import hexlet.code.Differ.Parser.JsonParser;
import hexlet.code.Differ.Parser.Parser;
import hexlet.code.Differ.Parser.YamlParser;

import java.io.IOException;
import java.util.Map;

public class ParserFactory {
    public static Parser getParser(String pathFile) {
        if (pathFile.endsWith(".yaml") || pathFile.endsWith(".yml")) {
            return new YamlParser();
        } else if (pathFile.endsWith(".json")) {
            return new JsonParser();
        }
        return new Parser() {
            @Override
            public Map<String, Object> unserializeToMap(String pathFile) throws IOException {
                throw new IOException("File does not fit to type json or yaml");
            }

            @Override
            public String serialize(Object value) {
                return null;
            }
        };
    }
}
