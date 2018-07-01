package tool;

import org.apache.commons.io.FilenameUtils;
import parser.HorndroidParser;
import parser.IParser;

import java.io.*;

/**
 * Responsible for defining the properties of the HornDroid Analysis Tool. Especially in terms of required commands
 * to start the analysis and commands to clean the output.
 * It extends the abstract Tool class which implements the Tool Interface.
 *
 * The tools results are saved in the log file app.log, which can be found here ~/horndroid/target/logs/app.log
 *
 * @author Timo Spring
 * @see Tool
 * @see <a href="https://github.com/ylya/horndroid/blob/master/README.md">HornDroid Project Site</a>
 */
public class HorndroidTool extends Tool {

    private String workingDir = System.getProperty("user.dir");
    private String susiPath = workingDir + "/../tools/commonConfig/SourcesAndSinks.txt";


    // TODO Check if you want to give it apk folder path instead of file path
    public HorndroidTool(String apkPath){
        super(apkPath);
        this.toolDir = homeDir + "/tools/horndroid";
        this.toolName = "horndroid";
        this.resultsFileName = getResultsFileName();
        setCommand();
        this.matrixIndex = 1;

    }

    /**
     * Special Constructor mainly used for testing.
     */
    public HorndroidTool(String apkPath, String androidSdk){
        super(apkPath, androidSdk);
        this.toolDir = homeDir + "/tools/horndroid";
        this.toolName = "horndroid";
        this.resultsFileName = getResultsFileName();
        setCommand();
    }

    /**
     * Sets the command used to trigger the horndroid analysis i.e. to start the shell script providing the
     * path to the apk file. Must be a valid command. It also uses the apkTool to prepare the apk file for the
     * analysis.
     */
    /*@Override
    protected void setCommand() {
        command = "~/local/jdk1.8.0_162/bin/java -jar fshorndroid-0.0.1.jar / ./apktool.jar " + apkPath;

    }*/


    @Override
    protected void setCommand(){
        command = "java -jar fshorndroid-0.0.1.jar / ./apktool.jar " + apkPath;
    }



    /**
     * Gets the file containing the results from the analysis.
     * @return File - with extension .log. Contains the results from the analysis.
     */
    @Override
    public File getResultsFile() {
        //String resultsPath = toolDir + "/logs/" + getResultsFileName();
        String resultsPath = toolDir + "/OUTPUT.report/" + getResultsFileName();
        File result = new File(resultsPath);
        assert result.isFile();
        return result;
    }

    /**
     * Cleans the output produced by HornDroid. First we have to empty the logs file inside the horndroid/logs
     * directory. Then we have to delete the analysis folder created by the apk tool in the "apk_sample" folder in the
     * workspace home directory. For that purpose we have to get the toolName of the apk without extensions, since this will
     * be the toolName of the folder that was created.
     *
     * To start the cleaning session, it calls the ShellExecutor and passes it the directory and cleaning command as
     * Strings.
     */
    @Override
    public void cleanToolOutput() {
        String apksSourceFolder = homeDir + "/apksToTest/";
        String cleanCommand = "cat /dev/null > " + toolDir + "/logs/app.log; " +
                "rm -r " + apksSourceFolder + getFileNameWithoutExtension(apkPath)
                + "; rm -r OUTPUT.report/" + getResultsFileName();
        Tool.shell.runCommand(toolDir, cleanCommand);
    }

    /**
     * Gets the toolName of a file without the file type extension. It takes the path to the file as a string, creates a new File
     * of that path and uses the .getToolName() method to get the toolName of the file at the end of the path. Afterwards, it uses
     * the FilenameUtils to remove the extension. The base toolName of the file is required to clean the ouptut folder created
     * by the apktool inside the "apk_sample" folder.
     *
     * @param filePath - string path to the file, must be  a valid string
     * @return the base toolName of the file (at which the path pointed to)
     */
    private String getFileNameWithoutExtension(String filePath){
        File file = new File(filePath);
        String fileName = file.getName();
        return FilenameUtils.getBaseName(fileName);
    }

    @Override
    protected IParser setParser(File file)
    {
        return new HorndroidParser(file);
    }

	/**
	 * Not relevant for horndroid, as we do not need stdout.
	 * @return
	 */
	@Override
	public String getResultsFileName()
    {
        return appName + ".apk.json";
	}


}
