package hexlet.code.Factories;

import hexlet.code.Formatter.Formatter;
import hexlet.code.Formatter.JsonFormatter;
import hexlet.code.Formatter.PlainFormatter;
import hexlet.code.Formatter.StylishFormatter;

public class FormatterFactory {
    public static Formatter getFormatter(String formatName) {
        if (formatName.equals("plain")) {
            return new PlainFormatter();
        } else if (formatName.equals("json")) {
            return new JsonFormatter();
        }
        return new StylishFormatter();
    }
}
