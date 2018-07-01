package writer;

import leaks.Leak;
import tool.ITool;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Writer {
    private ArrayList<ITool> tools;
    private String resultsFolder;
    private static final String DIVIDER = "----------------------------------------------------------------------------\n";


    public Writer(ArrayList<ITool> tools){
        this.tools = tools;
        resultsFolder = System.getProperty("user.dir") + "/../results";
    }

    public void writeToTxtFile(ArrayList<Leak> leaks, String appName, String matchingMatrix, String timedOut){
        String fileName = resultsFolder + "/summary_" + appName + ".txt";
        StringBuilder builder = buildResultsTxtFile(leaks, appName, matchingMatrix, timedOut);
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: Results could not be written to summary.txt file");
        }

        System.out.println(appName + ": Summarized results saved here ~/results/summary_" + appName + ".txt");
    }

    private StringBuilder buildResultsTxtFile(ArrayList<Leak> leaks, String appName, String matchingMatrix, String timedOut) {
        StringBuilder builder = new StringBuilder();
        builder.append(appName.toUpperCase()).append(".apk\n");
        builder.append(DIVIDER);

        for(Leak leak: leaks){
            builder.append(leak.toString()).append("\n");
            builder.append(DIVIDER);
        }
        builder.append(DIVIDER).append(DIVIDER);
        builder.append("PERFORMANCE: \n");

        for(ITool tool: tools){
            builder.append("Running Time: ");
            builder.append(tool.getToolName()).append(":").append(tool.getDuration()).append("\n");
        }
        builder.append("Total Running Time for APK: ");
        builder.append(tools.get(tools.size()-1).getTotalDuration()).append("\n\n");

        builder.append(matchingMatrix);
        builder.append(timedOut);
        return builder;
    }

    public void writeToCsvFile(ArrayList<Leak> leaks, String appName){
        String fileName = resultsFolder + "/csv/summary_" + appName + ".csv";
        File file = new File(fileName);
        String header = "App Name;Class Name;Method Name;Method Return;Sink Method Name;Sink Method Return;flowdroid;horndroid;iccta;covert;ic3;Number of Matches\n";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            if(file.length()==0){
                writer.write(header);
            }
            for(Leak leak : leaks){
                String leakString = leak.getCSVRepresentation(";");
                writer.write(leakString);
            }
            writer.flush();
            writer.close();
            writeTimedOutToCsv(appName);
        } catch (IOException e) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            writeToCsvFile(leaks, appName);
            System.out.println("Error: Could not find the summary.csv file - " + e.getMessage());
        }
    }

    private void writeTimedOutToCsv(String appName) {
        String fileName = resultsFolder + "/csv/timedOut/summary_timedOut" + appName + ".csv";
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String timeOut = getTimeOutStringRepresenation(appName);
            String header = "App name;flowdroid;horndroid;iccta;covert;ic3\n";
            if(file.length()==0){
                writer.write(header);
            }
            writer.write(timeOut);
            writer.flush();
            writer.close();
        }catch (IOException e) {
            System.out.println("Error: Could not find the summary.csv file - " + e.getMessage());
        }
    }


    private String getTimeOutStringRepresenation(String appName){
        StringBuilder builder = new StringBuilder();
        builder.append(appName).append(";");
        String[] timeOut = new String[tools.size()];
        for(ITool tool: tools){
            int index = tool.getMatrixIndex();
            if(tool.getExitCode()){
                timeOut[index] = "1";
            }else timeOut[index] = "0";
        }

        for(String tool : timeOut){
            builder.append(tool).append(";");
        }
        builder.append("\n");
        return builder.toString();
    }
}
