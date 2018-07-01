package tool;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Responsible for Testing the IC3Tool properties i.e. its specific implementation of the Tools class.
 *
 * @author Timo Spring
 */
public class Ic3ToolTest {
    private ITool droid;
    private static final String HOMEDIR = "home";
    private static final String APKPATH = "apk/app.apk";
    private static final String SDK = "sdk";
    private static final String COMMAND = "./runIC3.sh " + APKPATH + " > app-ic3Results.txt";

    @Before
    public void setUp(){
        droid = new Ic3Tool(APKPATH, SDK);
        droid.setHomeDir(HOMEDIR);

    }

    @Test
    public void getCommand() {
        assertEquals(COMMAND, droid.getCommand());
    }

    @Test
    public void setCommand() {
        ITool droids = new Ic3Tool(APKPATH, "sdk");
        String command = "./runIC3.sh " + "apk/app.apk > app-ic3Results.txt";
        assertEquals(command, droids.getCommand());

    }

    @Test
    public void cleanToolOuput(){
        //TODO add testcase for cleaning output
    }

    @Test
    public void runDare(){
        //TODO test that dare runs on apk.
    }

    @Test
    public void getResults(){
        //TODO add clever test
    }

    @Test
    public void getName(){
        String name = droid.getToolName();
        assertEquals("ic3", name);
    }
}