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
    public void testStylish() throws IOException {
        int exitCode = cmd.execute("src/test/resources/filesWithData/withNesting1.json",
                "src/test/resources/filesWithData/withNesting2.json");
        String expected = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expected/expectedDifferencesStylish.txt").toAbsolutePath()));
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testPlain() throws IOException {
        int exitCode = cmd.execute("src/test/resources/filesWithData/withNesting1.yml",
                "src/test/resources/filesWithData/withNesting2.yml", "-f=plain");
        String expected = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expected/expectedDifferencesPlain.txt").toAbsolutePath()));
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testJson() throws IOException {
        int exitCode = cmd.execute("src/test/resources/filesWithData/withNesting1.yml",
                "src/test/resources/filesWithData/withNesting2.yml", "-f=plain");
        String expected = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expected/expectedDifferencesPlain.txt").toAbsolutePath()));
        String actual = sw.toString();
        Assertions.assertEquals(0, exitCode);
        Assertions.assertEquals(expected, actual);
    }
}
