package shell.control;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Responsible for executing String commands in the console/shell/terminal. The string commands are being
 * passed by the Tools classes and are used to trigger the analysis tools on a given apk.
 * Given the string commands and the directory where the command should be executed in,
 * a builder is created, which then starts a process for the command execution.
 *
 * The boolean isTraceVisible will show or hide the console output during the running command.
 * If this boolean is false, then only a confirmation is shown after successful execution. To show the
 * console output, it uses the StreamHunter and Executors class.
 *
 * @author Timo Spring
 */
public class ShellExecutor {
    private ProcessBuilder builder;
    private static boolean isTraceVisible = false;
    private static final int TIMEOUT = 60;


    public ShellExecutor(){
        builder = new ProcessBuilder();

    }

    /**
     * Configures the format of accepted commands i.e. which underlying platform is used.
     * Furthermore, it adds the command to be executed in the shell and directory that it should
     * run in to the builder. Afterwards, the builder is ready to execute the command.
     *
     * @param directory - String, Path of directory in which command should be executed (working dir),
     *                  must be a valid directory
     * @param command - String, command to be executed in the shell,
     *                must be a valid command (depending on underlying platform)
     */
    private void setCommandForBuilder(String directory, String command){
        assert builder != null;

        /*if(isWindows()){
            builder.command("cmd.exe", "/c", command);
        }else {*/
            builder.command("sh", "-c", command);
        //}
        builder.redirectErrorStream(true);
        builder.directory(new File(directory));
    }


    /**
     * Runs the command string in the terminal/shell. Before that it triggers the builder configuration in the correct
     * location and. A new Process is started for the command execution, which should finish with exit code 0 in case
     * the command was successfully executed. Otherwise, an exception will be thrown.
     *
     * @param directory - String, Path of directory in which command should be executed (working dir),
     *                  must be a valid directory
     * @param command - String, command to be executed in the shell,
     *                must be a valid command (depending on underlying platform)
     * @return Integer exitCode - should be 0 if the execution returned successful. Otherwise, an exception will have
     *                  been thrown.
     */
    public boolean runCommand(String directory, String command){
        boolean exitCode = false;
        setCommandForBuilder(directory, command);
        System.out.println("Command: " + command);
        try {
            Process process = builder.start();
            //if(isTraceVisible){showConsoleTrace(process);}
            exitCode = process.waitFor(TIMEOUT, TimeUnit.MINUTES);

            if(!exitCode){
                process.destroyForcibly();
                System.out.println("TIME OUT: Aborting analysis.\n");
            }
        }catch(IOException | InterruptedException e){
            System.out.println("Error occurred while executing command. Error Description: " + e.getMessage());
        }
        return exitCode;
    }


    /**
     * Gathers and shows the console output during the running analysis. If disabled, the analysis will only return with a
     * String indicating if it was successful or not. It is achieved by creating a new StreamHunter object, to which
     * we are passing the input stream of our current process. We are then submitting the StreamHunter to the
     * SingleThreadExecutor.
     *
     * @param process - of Type Process, was started by the ProcessBuilder and responsible for executing the command.
     */
    /*private static void showConsoleTrace(Process process){
        StreamHunter hunter = new StreamHunter(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(hunter);

    }*/

    /**
     * Checks if the underlying platform is Windows, which has an impact on the expected command format i.e. the
     * shell is called with "cmd" instead of "sh".
     *
     * @return true if Windows, false otherwise (macOS or Linux)
     */
    private static boolean isWindows() {
        return System.getProperty("os.toolName").toLowerCase().startsWith("windows");
    }

    /**
     * Changes the visibility of the running analysis console output (
     * @param value - boolean
     */
    public static void setTraceVisibility(boolean value){
        isTraceVisible = value;
    }
}
