package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AppTest {

    @Test
    public void testStylish() throws IOException {
        String expected = getExpected("diffsStylish.txt");
        String actual = Differ.generate(
                getPathRawFile("file1.json"),
                getPathRawFile("file2.json"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testPlain() throws IOException {
        String expected = getExpected("diffsPlain.txt");
        String actual = Differ.generate(
                getPathRawFile("file1.yml"),
                getPathRawFile("file2.yml"),
                "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testJson() throws IOException {
        String expected = getExpected("diffsJson.txt");
        String actual = Differ.generate(
                getPathRawFile("file1.json"),
                getPathRawFile("file2.yml"),
                "json");
        Assertions.assertEquals(expected, actual);
    }

    private String getPathRawFile(String fileName) {
        return Path.of("src/test/resources/filesWithData/", fileName).toString();
    }

    private String getExpected(String expectedFile) throws IOException {
        Path path = Path.of("src/test/resources/expected/", expectedFile);
        return Files.readString(path);
    }
}
