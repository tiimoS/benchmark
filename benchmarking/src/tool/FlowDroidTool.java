package tool;

import parser.FlowDroidParser;
import parser.IParser;

import java.io.File;
import java.nio.file.Paths;

/**
 * Responsible for defining the properties of the FlowDroid Analysis Tool. Especially in terms of required commands
 * to start the analysis and commands to clean the output.
 * It extends the abstract Tool class which implements the Tool Interface.
 *
 * The tools results (console output) are saved in the flowdroid directory in the flowdroidResults.txt file
 *
 * @author Timo Spring
 * @see Tool
 * @see <a href="https://blogs.uni-paderborn.de/sse/tools/flowdroid/"> FlowDroid Project Site</a>
 */
public class FlowDroidTool extends Tool {
    private String resultsFile;


    public FlowDroidTool(String apkPath) {
        super(apkPath);
        this.toolDir = Tool.homeDir + "/tools/flowDroid";
        this.toolName = "flowdroid";
        this.resultsFile = getResultsFileName();
        this.androidSdk = toolDir + "/android.jar";
        this.matrixIndex = 0;
        setCommand();

    }

    /**
     * Special Constructor mainly used for testing.
     */
    public FlowDroidTool(String apkPath, String androidSdk) {
        super(apkPath, androidSdk);
        this.toolDir = Tool.homeDir + "/tools/flowDroid";
        this.toolName = "flowdroid";
        this.resultsFile = getResultsFileName();
        setCommand();
    }

    /**
     * Sets the command used to trigger the flowDroid analysis i.e. to start the shell script providing the
     * path to the apk file and android sdk. Must be a valid command. The command consists of even more
     * JAR files to be used from the flowdroid project directory.
     */
    @Override
    protected void setCommand() {
       command = "java -Xmx4g -cp soot-trunk.jar:" +
                "soot-infoflow.jar:soot-infoflow-android.jar:slf4j-api-1.7.5.jar:slf4j-simple-1.7.5.jar:axml-2.0.jar " +
                "soot.jimple.infoflow.android.TestApps.Test " +
                Tool.apkPath + " " + Tool.androidSdk + " > " + resultsFile;

       /*
        command = "~/local/jdk1.8.0_162/bin/java -Xmx4g -cp soot-trunk.jar:" +
                "soot-infoflow.jar:soot-infoflow-android.jar:slf4j-api-1.7.5.jar:slf4j-simple-1.7.5.jar:axml-2.0.jar " +
                "soot.jimple.infoflow.android.TestApps.Test " +
                Tool.apkPath + " " + Tool.androidSdk + " > " + resultsFile;*/
    }

    /**
     * Cleans the produced output to the flowdroidResults.txt file i.e. it deletes the file.
     */
    @Override
    public void cleanToolOutput() {
        String cleanCommand = "rm -r " + resultsFile;
        shell.runCommand(toolDir, cleanCommand);

    }

    /**
     * Gets the file containing the results from the analysis.
     * @return File - with extension .txt. Contains the results from the analysis.
     */
    @Override
    public File getResultsFile() {
        String resultsPath = toolDir + "/" + resultsFile;
        File results = new File(resultsPath);
        assert results.isFile();
        return results;
    }

    @Override
    protected IParser setParser(File file) {
        return new FlowDroidParser(file);
    }

}
