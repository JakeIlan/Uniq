import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

import java.io.IOException;
import java.util.Scanner;

public class UniqLauncher {

    @Option(name = "-i", usage = "Not case-sensitive")
    private boolean register = false;

    @Option(name = "-u", usage = "Uniq lines only")
    private boolean uniqLines = false;

    @Option(name = "-c", usage = "Counting replaced lines")
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

        try {
            if (!outputFileName.equals("")){
                File file = new File(outputFileName);
                if (file.exists()) {
                    System.out.println("Файл с выбранным именем уже свществует. Заменить его? Y/N");
                    Scanner scan = new Scanner(System.in);
                    String answer = scan.next();
                    if (answer.equals("Y")) {
                        if (file.delete()) {
                            System.out.println("Старый файл удалён успешно.");
                        }
                        Uniq uniq = new Uniq(register, uniqLines, countLines, ignoredChars, outputFileName, inputFileName);
                        uniq.writeUniq(inputFileName, outputFileName);
                        System.out.println("Входной файл обработан успешно.");
                        System.out.println("Конец программы.");
                    }
                    else {
                        if (answer.equals("N")) {
                            System.out.println("Вывести результат в консоль? Y/N");
                            answer = scan.next();
                            if (answer.equals("Y")){
                                System.out.println("Результат будет выведен на экран:");
                                System.out.println("-------------------");
                                System.out.println("");
                                Uniq uniq = new Uniq(register, uniqLines, countLines, ignoredChars, outputFileName, inputFileName);
                                uniq.writeUniq(inputFileName);
                                System.out.println("");
                                System.out.println("-------------------");
                                System.out.println("Входной файл обработан успешно.");
                                System.out.println("Конец программы.");
                            }
                            else System.out.println("Конец программы.");
                        }
                    }
                } else {
                    Uniq uniq = new Uniq(register, uniqLines, countLines, ignoredChars, outputFileName, inputFileName);
                    uniq.writeUniq(inputFileName, outputFileName);
                    System.out.println("Фходной файл обработан успешно.");
                    System.out.println("Конец программы.");
                }
            } else {
                System.out.println("Результат будет выведен на экран:");
                System.out.println("-------------------");
                System.out.println("");
                Uniq uniq = new Uniq(register, uniqLines, countLines, ignoredChars, outputFileName, inputFileName);
                uniq.writeUniq(inputFileName);
                System.out.println("");
                System.out.println("-------------------");
                System.out.println("Входной файл обработан успешно.");
                System.out.println("Конец программы.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
