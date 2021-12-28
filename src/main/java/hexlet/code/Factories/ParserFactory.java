package hexlet.code.Factories;

import hexlet.code.Parser.JsonParser;
import hexlet.code.Parser.Parser;
import hexlet.code.Parser.YamlParser;

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
        };
    }
}
