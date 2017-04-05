import java.io.*;

public class Uniq {

    private final String inputFileName;
    private final String outputFileName;
    private final int ignoredChars;
    private final boolean register;
    private final boolean uniqOnly;
    private final boolean count;

    public Uniq(boolean register, boolean uniqOnly, boolean count,
                int ignoredChars, String outputFileName, String inputFileName){
        this.register = register;
        this.uniqOnly = uniqOnly;
        this.count = count;
        this.ignoredChars = ignoredChars;
        this.outputFileName = outputFileName;
        this.inputFileName = inputFileName;
    }

    private void writeFileString(String str, OutputStream out) throws IOException{
        for (int i = 0; i < str.length(); i++){
            out.write(str.charAt(i));
        }
    }

    public void writeUniq(InputStream in, OutputStream out) throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
                String line = reader.readLine();
                line = line.substring(ignoredChars, line.length());
                String prevLine = line;
                Integer count = 1;
                while (reader.readLine() != null) {
                    line = reader.readLine();
                    if (register) {
                        if (line.toLowerCase().equals(prevLine.toLowerCase())) {
                            count++;
                        } else {
                            if (this.count) writeFileString(count.toString() + " ", out);
                            writeFileString(prevLine, out);
                        }
                    } else {
                        if (line.equals(prevLine)) {
                            count++;
                        } else {
                            if (this.count) writeFileString(count.toString() + " ", out);
                            writeFileString(prevLine, out);
                        }
                    }
                    prevLine = line;
                    count = 1;
                }
            }
        }
    }

    public void writeUniq(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputFileName)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputFileName, true)) {
                writeUniq(inputStream, outputStream);
            }
        }
    }



}
