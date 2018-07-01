package tool;


import parser.CovertParser;
import parser.IParser;

import java.io.File;

/**
 * Responsible for defining the properties of the Covert Analysis Tool. Especially in terms of required commands
 * to start the analysis and commands to clean the output.
 * It extends the abstract Tool class which implements the Tool Interface.
 *
 * The tools results are saved in the .xml file ~/covert/app_repo/apksToTest/apksToTest.xml.
 *
 * @author Timo Spring
 * @see Tool
 * @see <a href="http://www.ics.uci.edu/~seal/projects/covert/">Covert Project Site</a>
 */
public class CovertTool extends Tool {
    private String testFolderName;

    public CovertTool(String apkPath){
        super(apkPath);
        this.toolDir = Tool.homeDir + "/tools/covert";
        this.toolName = "covert";
        this.resultsFileName = getResultsFileName();
        this.testFolderName = appName;
        this.matrixIndex = 3;
        setCommand();

    }

    /**
     * Special Constructor mainly used for testing.
     */
    public CovertTool(String apkPath, String androidSdk) {
        super(apkPath, androidSdk);
        this.toolDir = Tool.homeDir + "/tools/covert";
        this.toolName = "covert";
        this.resultsFileName = getResultsFileName();
        this.testFolderName = appName;
        setCommand();
    }

    /**
     * Sets the command used to trigger the covert analysis i.e. to start the shell script providing the
     * path to the apk file. Must be a valid command.
     */
    @Override
    protected void setCommand() {
        command = "./covert.sh " + testFolderName;

    }

    /**
     * Runs the analysis by first transferring all apks to test to the internal "app_repo" i.e. the "apksToTest" subfolder
     * of covert. Secondly, it will call the ShellExecutor with the covert root directory and the String command to
     * start the tools execution.
     */
    @Override
    public boolean runAnalysis(){
        long start = System.currentTimeMillis();
        transferTestsourcesToCovert();
        exitCode = Tool.shell.runCommand(toolDir, command);
        long end = System.currentTimeMillis();
        duration = end - start;
        totalDuration += duration;
        return exitCode;
    }

	/**
	 * Not relevant for CovertTool, as we do not need stdout.
	 * @return
	 */
	@Override
	public String getResultsFileName() {
    	return appName + ".xml";
	}

	/**
     * Gets the file containing the results from the analysis.
     * @return File - with extension .xml. Contains the results from the analysis.
     */
    @Override
    public File getResultsFile() {
        String resultsPath = toolDir + "/app_repo/" + testFolderName + "/" + testFolderName + ".xml";
        File results = new File(resultsPath);
        assert results.isFile();
        return results;
    }

    @Override
    protected IParser setParser(File file) {
       return new CovertParser(file);
    }

    /**
     * Cleans the output produced by Covert i.e. removes the test folder "apksToTest" which contains the analysis
     * results and re-creates an empty one. The apksToTest folder can be found in the Covert directory under "app_repo".
     * The apks to test must be in this folder for the analysis to run properly.
     * To start the cleaning session, it calls the ShellExecutor and passes it the directory and cleaning command as
     * Strings.
     */
    @Override
    public void cleanToolOutput() {
        String cleanCommand = "rm -r " + testFolderName;
        String covertOutput = toolDir + "/app_repo";
        Tool.shell.runCommand(covertOutput, cleanCommand);
        //TODO check that directories are clean/empty
    }

    /**
     * Transfers the apks in the "apk_sample" folder in the home directory to a tool specific subfolder in the covert
     * directory. This is required for the covert tool to run properly. It does not run, if you simply provide the path
     * to the external apk folder. The folder must be withing the covert directory. Only then can the analysis be run.
     * Must be valid commands.
     */
    private void transferTestsourcesToCovert(){
	    String mkdir = "mkdir " + toolDir + "/app_repo/" + testFolderName;
	    Tool.shell.runCommand(toolDir, mkdir);
        String transferCommand = "cp " + Tool.apkPath + " " + toolDir + "/app_repo/" + testFolderName;
        Tool.shell.runCommand(toolDir, transferCommand);
        //TODO check that directory is not empty
    }





}
