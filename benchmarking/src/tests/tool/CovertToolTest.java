package tool;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
//TODO surpress console output

/**
 * Responsible for Testing the CovertTool properties i.e. its specific implementation of the Tools class.
 *
 * @author Timo Spring
 */
public class CovertToolTest {
    private ITool droid;
    private static final String HOMEDIR = "home";
    private static final String APKPATH = "app/app.apk";
    private static final String SDK = "sdk";
    private static final String COMMAND = "./covert.sh app";

    @Before
    public void setUp() {
        droid = new CovertTool(APKPATH, SDK);
        droid.setHomeDir(HOMEDIR);

    }

    @Test
    public void getCommand() {
        assertEquals(COMMAND, droid.getCommand());
    }

    @Test
    public void setCommand() {
        ITool droids = new CovertTool("apkPath/app.apk", "sdk");
        String command = "./covert.sh app";
        assertEquals(command, droids.getCommand());

    }

    @Test
    public void cleanToolOuput(){
        //TODO add testcase for cleaning output
    }

    @Test
    public void transferTestsourcesToCovert(){
        //TODO add testcase for transferring testsources to covert folder
    }


    @Test
    public void getName(){
        String name = droid.getToolName();
        assertEquals("covert", name);

    }
}