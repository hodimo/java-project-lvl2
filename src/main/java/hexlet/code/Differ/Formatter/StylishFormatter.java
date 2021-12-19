package hexlet.code.Differ.Formatter;

import hexlet.code.Differ.Parser.Parser;

import java.io.IOException;
import java.util.Map;

public class StylishFormatter extends Formatter {
    @Override
    public final String format(Map<String, Object> differences, Parser parser) throws IOException {
        String diffs = parser.serialize(differences);
        return diffs
                .replaceAll("\"\"", "")
                .replaceAll("\"", "")
                .replaceAll("unchanged: ", "\n    ")
                .replaceAll("removed: ", "\n  - ")
                .replaceAll("added: ", "\n  + ")
                .replaceAll(",\\n", "\n")
                .replaceAll("(  ( )|(\\+)|(-))(.+?:)", "$1$5 ")
                .replaceAll("((( {4})|( {2}- )|( {2}\\+ )).+:) \\n", "$1\n")
                .replaceAll(",", ", ")
//                .replaceAll("")
                .replaceAll("}$", "\n}");
    }
}
