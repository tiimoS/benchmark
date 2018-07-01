package tool;

import leaks.Leak;
import parser.IParser;

import java.io.File;
import java.util.ArrayList;

/**
 * Responsible for providing the general commands to trigger the analysis tools to run in a unified way.
 * It also provides means to get the String commands used to run the analysis and to change the home directory (
 * which is currently the root of the droid-Security-Thesis workspace). Furthermore, it provides the possibility to
 * clean the output, that was created by the tools. This may be .txt, .xml files or other folders with reports. This
 * comes in handy if you want to run the analysis on bigger data sets.
 *
 * The Interface gets implemented by the abstract Tool class which is in turn extended by a class for each tool e.g.
 * FlowDroidTool. So we can use polymorphism here.
 *
 * @author Timo Spring
 */
public interface ITool {

    /**
     * Runs the analysis for the given tool e.g. of type FlowDroidTool. It does so by calling the ShellExecutor and
     * passing it the directory where the command should be executed in and the command as a string. The ShellExecutor
     * will then take care of the execution and console output.
     */
     boolean runAnalysis();

    /**
     * Cleans and deletes all produced outputs by the tools such as .txt or .xml reports and special folders that were
     * created during the analysis.
     */
    void cleanToolOutput();

    /**
     * Returns the file, which contains the results of the analysis.
     *
     * @return File of type File containing the results. This may be a .txt, .xml or .log file.
     */
    File getResultsFile();

    /**
     * Gets the command to a given Tool Object. Since the String commands used to trigger the analysis to run are
     * highly diverse depending on the tools, we provide a mean to check these commands.
     *
     * @return command as a String, used to trigger analysis to run in shell.
     */
    String getCommand();

    /**
     * Changes the used home directory. By default it points to the root of the droid-security-thesis workspace (from
     * gitHub) i.e. the parent folder of the benchmarking project. That's because this directory contains the "tools"
     * folder containing all required JARs and work files to run the individual analysis. Furthermore, it contains the
     * "apk_sample" folder, which contains the test apks to be run the analysis with. The home directory is currently
     * specified by taking the working directory (benchmarking folder) and then navigating to its parent folder.
     *
     * However, you have the possibility to change the home directory if required. This is mainly used for testing.
     * @param homedir - String path to the home directory i.e. the folder containing all workspace data such as the
     *                "tools" folder or the "apk_sample" folder. Currently set to "droid-Security-Thesis" folder.
     *                Must be a valid path to directory.
     */
    void setHomeDir(String homedir);

    /**
     * Gets the path to the directory in which the tool runs as a String.
     * @return String path to the tool's root directory.
     */
    String getToolDir();

    /**
     * Gets the commercial toolName of the tool.
     * @return String with the toolName of the tool
     */
    String getToolName();

    ArrayList<Leak> getLeaks();

    String getDuration();

    String getTotalDuration();

    int getMatrixIndex();

    String getAppName();

    boolean getExitCode();
}
