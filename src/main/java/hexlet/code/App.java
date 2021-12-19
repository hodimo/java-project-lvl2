package hexlet.code;

import hexlet.code.Differ.Differ;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;


@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")

class App implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @Parameters(index = "0", defaultValue = "/home/proydemte/jsons/json1.json", description = "path to first file")
    private String filepath1 = "";

    @Parameters(index = "1", defaultValue = "/home/proydemte/jsons/json2.json", description = "path to second file")
    private String filepath2 = "";

    @Option(names = {"-f", "--format"}, paramLabel = "format",
            defaultValue = "stylish", description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        spec.commandLine().getOut().println(Differ.differ(filepath1, filepath2, format));
        return 0;
    }

    public static void main(String[] args) {
        try {
            int exitCode = new CommandLine(new App()).execute(args);
            System.exit(exitCode);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
