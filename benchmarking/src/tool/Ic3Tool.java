package tool;

import parser.IC3Parser;
import parser.IParser;

import java.io.File;

/**
 * Responsible for defining the properties of the IC3 Analysis Tool, which is a replacement
 * for the outdated Epicc tool. It defines the required commands
 * to start the analysis and commands to clean the output.
 * It extends the abstract Tool class which implements the Tool Interface.
 *
 * The results are saved in a new .txt file in the ~/ic3/ic3output directory with the
 * naming convention [apk_package_name].txt
 *
 * @author Timo Spring
 * @see Tool
 * @see <a href="http://siis.cse.psu.edu/ic3/index.html">IC3 Project Site</a>
 */
public class Ic3Tool extends Tool {

    // TODO Check if you want to give it apk folder path instead of file path
    public Ic3Tool(String apkPath) {
        super(apkPath);
        this.toolDir = Tool.homeDir + "/tools/ic3";
        this.toolName = "ic3";
        this.resultsFileName = getResultsFileName();
        this.matrixIndex = 4;
        setCommand();

    }

    /**
     * Special Constructor mainly used for testing.
     */
    public Ic3Tool(String apkPath, String androidSdk) {
        super(apkPath, androidSdk);
        this.toolDir = Tool.homeDir + "/tools/ic3";
        this.toolName = "ic3";
        this.resultsFileName = getResultsFileName();
        setCommand();
    }

    /**
     * Sets the command used to trigger the ic3 analysis i.e. to start the shell script providing the
     * path to the apk file. Must be a valid command.
     */
    @Override
    protected void setCommand() {
        command = "./runIC3.sh " + apkPath + " > " + getResultsFileName();

    }

    /**
     * Runs the analysis by first calling the "Dare" helper tool which prepares the
     * apks i.e. extracts classes from apks and dex files. Then it runs the analysis by calling its parents
     * runAnalysis method.
     */
    @Override
    public boolean runAnalysis() {
        long start = System.currentTimeMillis();
        if(runDare()) {
            exitCode = shell.runCommand(toolDir, command);
            long end = System.currentTimeMillis();
            duration = end - start;
            totalDuration += duration;
            return exitCode;
        }else
        return false;
    }

	/**
     * Gets the file containing the results from the analysis.
     * @return File - with extension .txt. Contains the results from the analysis.
     */
    @Override
    public File getResultsFile() {
        String resultsPath = toolDir + "/" + getResultsFileName();
        File result = new File(resultsPath);
        assert result.isFile();
        return result;
    }

    /**
     * Cleans the output produced by the ic3 tool. Since the tool uses dare, it
     * cleans all dareOutput folders and ic3output folders. It does so by passing
     * the corresponding commands to the ShellExecutor to start the cleaning session.
     * Must be valid commands.
     */
    @Override
    public void cleanToolOutput() {
	    String cleanCommand = "cd dareOutput/optimized; rm -r " + appName + "; cd ../optimized-decompiled; rm -r " + appName + "; cd ../retargeted; rm -r " + appName
                + "; cd ..; rm -r stats.csv; cd ..; rm -r " + getResultsFileName();
        Tool.shell.runCommand(toolDir, cleanCommand);
    }

    /**
     * Runs the Dare Tool to extract .classes from .apks and .dex files. This is required for
     * the IC3 Tool to run. It does so by executing the shell script defined in the
     * dare directory i.e. by passing it as a command to the ShellExecutor.
     * Must be a valid command and directory.
     *
     * @see <a href="http://siis.cse.psu.edu/ded/">Dare Project Site</a>
     */
    private boolean runDare() {
        String dareRoot = homeDir + "/tools/tools_helper/dare";
        String dareOutput = homeDir + "/tools/ic3/dareOutput/"; //+ appName;
        String dareCommand = "./dare -d " + dareOutput + " " + apkPath;
        boolean exitCode = Tool.shell.runCommand(dareRoot, dareCommand);
        return exitCode;
    }

    @Override
    protected IParser setParser(File file) {
        return new IC3Parser(file);
    }
}



