//package hexlet.code;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import picocli.CommandLine;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//public class AppTest {
//
////    ByteArrayOutputStream baos;
////    PrintStream ps;
////    PrintStream old;
////
////    @BeforeEach
////    public void prepareOut() {
////        baos = new ByteArrayOutputStream();
////        ps = new PrintStream(baos);
////        old = System.out;
////        System.setOut(ps);
////    }
////
////    @AfterEach
////    public void resetOut() {
////        System.out.flush();
////        System.setOut(old);
////    }
//
//    private static App app;
//    private static CommandLine cmd;
//    private static StringWriter sw;
//
//    @BeforeEach
//    public void prepareParams() {
//        app = new App();
//        cmd = new CommandLine(app);
//        sw = new StringWriter();
//        cmd.setOut(new PrintWriter(sw));
//    }
//
//    @Test
//    public void testGendiff() {
//        int exitCode = cmd.execute("-h");
//        String actual = sw.toString();
//        Assertions.assertEquals(0, exitCode);
//        Assertions.assertAll("actual",
//                Assertions.assertTrue(actual.matches()););
//    }
//}
