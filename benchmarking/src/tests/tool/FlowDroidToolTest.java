package tool;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Responsible for Testing the FlowDroidTool properties i.e. its specific implementation of the Tools class.
 *
 * @author Timo Spring
 */
public class FlowDroidToolTest {
    private static ITool droid;
    private static final String HOMEDIR = "home";
    private static final String APKPATH = "apk/app.apk";
    private static final String SDK = "sdk";
    private static final String COMMAND = "java -Xmx4g -cp soot-trunk.jar:" +
            "soot-infoflow.jar:soot-infoflow-android.jar:slf4j-api-1.7.5.jar:slf4j-simple-1.7.5.jar:axml-2.0.jar " +
            "soot.jimple.infoflow.android.TestApps.Test apk/app.apk sdk > app-flowdroidResults.txt";

    @Before
    public void setUp() throws Exception {
        droid = new FlowDroidTool(APKPATH, SDK);
        droid.setHomeDir(HOMEDIR);

    }

    @Test
    public void getCommand() {
        assertEquals(COMMAND, droid.getCommand());
    }

    @Test
    public void setCommand() {
        ITool droids = new FlowDroidTool(APKPATH, "androidSdk");
        String command = "java -Xmx4g -cp soot-trunk.jar:" +
                "soot-infoflow.jar:soot-infoflow-android.jar:slf4j-api-1.7.5.jar:slf4j-simple-1.7.5.jar:axml-2.0.jar " +
                "soot.jimple.infoflow.android.TestApps.Test apk/app.apk androidSdk > app-flowdroidResults.txt";
        assertEquals(command, droids.getCommand());

    }



    @Test
    public void getName(){
        String name = droid.getToolName();
        assertEquals("flowdroid", name);

    }

    //TODO create test for cleaning output

}