import java.io.*;
import java.util.ArrayList;

public class SuSiPreParser {


    public static void main(String[] args){
        File susi = new File(args[0]);
        File horndroidSusi = new File(args[1]);
        assert susi.isFile() && horndroidSusi.isFile();

        ArrayList<String> fileLines = readFile(susi);
        try {
            FileWriter fileWriter = new FileWriter(horndroidSusi.getPath());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(String line : fileLines) {
                String modifiedLine = line.replaceAll("(?<=>) .* -> _SOURCE_", " -> _SOURCE_");
                String modifiedSourceLine = modifiedLine.replaceAll("(?<=>) .* -> _SINK_", " -> _SINK_");
                String modifiedSinkLine = modifiedSourceLine.replaceAll("\\|.*","");
                writer.write(modifiedSinkLine);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: Could not read and parse SourcesAndSinks.txt file - " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static ArrayList<String> readFile(File file){
        ArrayList<String> fileAsLines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine())!= null){
                fileAsLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error: File " + file.getName() + " could not be read." + e.getMessage());
        }

        return fileAsLines;
    }
}
