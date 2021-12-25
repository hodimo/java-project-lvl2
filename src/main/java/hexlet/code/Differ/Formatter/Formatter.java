package hexlet.code.Differ.Formatter;

import hexlet.code.Differ.Parser.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Formatter {
    public abstract String format(Map<String, List<Object>> differences, Parser parser) throws IOException;
}
