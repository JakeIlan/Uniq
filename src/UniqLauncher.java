import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class UniqLauncher {

    @Option(name = "-i", usage = "Not case-sensitive")
    private boolean register = false;

    @Option(name = "-u", usage = "Uniq lines only")
    private boolean uniqLines = false;

    @Option(name = "-с", usage = "Counting replaced lines")
    private boolean countLines = false;

    @Option(name = "-s", metaVar = "num", usage = "Ignoring first N chars")
    private int ignoredChars = 0;

    @Option(name = "-o", metaVar = "OutputFile", usage = "output File Name")
    private String outputFileName = "";

    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new UniqLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Uniq.jar -i -u -c -s Num -o OutputName InputName");
            parser.printUsage(System.err);
            return;
        }

        Uniq uniq = new Uniq(register, uniqLines, countLines, ignoredChars, outputFileName, inputFileName);
        try {
            uniq.writeUniq(inputFileName, outputFileName);
            System.out.println("Файл успешно обработан");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
