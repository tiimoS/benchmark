package test;

import org.junit.Before;
import org.junit.Test;
import shell.control.ShellExecutor;

import static org.junit.Assert.*;

/**
 * Responsible for Testing the ShellExecutor i.e. making sure that the string commands are executed in the shell.
 *
 * @author Timo Spring
 */
public class ShellExecutorTest {
    private ShellExecutor shell;

    @Before
    public void setUp() {
        shell = new ShellExecutor();
    }

    @Test
    public void runCommand() {
        String command = "ls";
        String dir = System.getProperty("user.dir");
        boolean exitCode = shell.runCommand(dir, command);
        assertEquals(true, exitCode);
    }

    @Test
    public void runCommandTraced(){
        String command = "ls";
        String dir = System.getProperty("user.dir");
        shell.setTraceVisibility(true);
        boolean exitCode = shell.runCommand(dir, command);
        assertEquals(true, exitCode);
    }

    //TODO add edge cases i.e. faulty commands.

}