package hexlet.code.Differ.Factories;

import hexlet.code.Differ.Formatter.Formatter;
import hexlet.code.Differ.Formatter.StylishFormatter;

public class FormatterFactory {
    public static Formatter getFormatter(String formatName) {
        return new StylishFormatter();
    }
}
