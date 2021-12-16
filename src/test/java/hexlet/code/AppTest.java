package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class AppTest {

    private static CommandLine cmd;
    private static StringWriter sw;

    @BeforeEach
    public void prepareParams() {
        App app = new App();
        cmd = new CommandLine(app);
        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));
    }

    @Test
    public void testAppHelp() throws IOException {
        int exitCode = cmd.execute("-h");
        String expected = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expectedAppHelp.txt").toAbsolutePath()));
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testAppVersion() {
        int exitCode = cmd.execute("-V");
        String expected = "gendiff 1.0\n";
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testAppWithStylishJson() throws IOException {
        int exitCode = cmd.execute("file1.json", "file2.json");
        String expected = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expectedDifferences.txt").toAbsolutePath()));
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testAppWithStylishYaml() throws IOException {
        int exitCode = cmd.execute("file1.yml", "file2.yml");
        String expected = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expectedDifferences.txt").toAbsolutePath()));
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testAppWithStylishViaRegex() {
        int exitCode = cmd.execute("file1.json", "file2.json");
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertTrue(actual.matches("\\{\\n(\\s(\\+|-|\\s)\\s.+\\n)+}\\n"));
    }
}
