package tool;

import parser.IParser;
import parser.IcctaParser;

import java.io.File;

/**
 * Responsible for defining the properties of the IccTA Analysis Tool. Especially in terms of required commands
 * to start the analysis and commands to clean the output.
 * It extends the abstract Tool class which implements the Tool Interface.
 *
 * The tools results (console output) are saved in the iccta directory in a .txt file named icctaResults.txt
 *
 * @author Timo Spring
 * @see Tool
 * @see <a href="https://sites.google.com/site/icctawebpage/">IccTA Project Site</a>
 */
public class IcctaTool extends Tool {

    // TODO Check if you want to give it apk folder path instead of file path
    public IcctaTool(String apkPath){
        super(apkPath);
        this.toolDir = Tool.homeDir + "/tools/iccTA";
        this.toolName = "iccta";
        this.resultsFileName = getResultsFileName();
        this.androidSdk = toolDir + "/android-platforms/android-18/android.jar";
        this.matrixIndex = 2;
        setCommand();

    }

    /**
     * Special Constructor mainly used for testing.
     */
    public IcctaTool(String apkPath, String androidSdk){
        super(apkPath, androidSdk);
        this.toolDir = Tool.homeDir + "/tools/iccTA";
        this.toolName = "iccta";
        this.resultsFileName = getResultsFileName();
        setCommand();
    }

    /**
     * Sets the command used to trigger the covert analysis i.e. to start the shell script providing the
     * path to the apk file. Must be a valid command.
     */
    @Override
    protected void setCommand() {
        command = "java -jar IccTA.jar " + apkPath + " " + androidSdk +
                " -iccProvider ./iccProvider/ic3/ > " + getResultsFileName();
    }

    /**
    * So far nothing to do here since flowdroid only outputs the results to the console.
    */
    @Override
    public void cleanToolOutput() {
        String cleanCommand = "rm -r " + getResultsFileName();
        shell.runCommand(toolDir, cleanCommand);
    }

    /**
     * Gets the file containing the results from the analysis.
     * @return File - with extension .txt. Contains the results from the analysis.
     */
    @Override
    public File getResultsFile() {
        String resultsPath = toolDir + "/" + getResultsFileName();
        File results = new File(resultsPath);
        assert results.isFile();
        return results;
    }

    @Override
    protected IParser setParser(File file)
    {
        return new IcctaParser(file);
    }

}
