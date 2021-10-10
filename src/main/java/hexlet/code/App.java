package hexlet.code;

import hexlet.code.Differ.Differ;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;


@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")

class GenDiff implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file", defaultValue = "/home/proydemte/jsons/json1.json")
    String filepath1 = "";

    @Parameters(index = "1", description = "path to second file", defaultValue = "/home/proydemte/jsons/json2.json")
    String filepath2 = "";

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    String format = "stylish";

    @Override
    public Integer call() throws Exception {
        System.out.println(Differ.generate(filepath1, filepath2));
        return 0;
    }

}

public class App {
    public static void main(String[] args) {
        try {
            int exitCode = new CommandLine(new GenDiff()).execute(args);
            System.exit(exitCode);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}