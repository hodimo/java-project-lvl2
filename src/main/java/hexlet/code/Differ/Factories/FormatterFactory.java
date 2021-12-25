package hexlet.code.Differ.Factories;

import hexlet.code.Differ.Formatter.Formatter;
import hexlet.code.Differ.Formatter.PlainFormatter;
import hexlet.code.Differ.Formatter.StylishFormatter;

public class FormatterFactory {
    public static Formatter getFormatter(String formatName) {
        if (formatName.equals("plain")) {
            return new PlainFormatter();
        }
        return new StylishFormatter();
    }
}
