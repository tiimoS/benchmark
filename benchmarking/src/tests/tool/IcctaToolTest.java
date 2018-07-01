package tool;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Responsible for Testing the IccTA properties i.e. its specific implementation of the Tools class.
 *
 * @author Timo Spring
 */
public class IcctaToolTest {
    private static ITool droid;
    private static final String HOMEDIR = "home";
    private static final String APKPATH = "apk/app.apk";
    private static final String SDK = "sdk";
    private static final String COMMAND = "java -jar IccTA.jar " + APKPATH + " " + SDK +
            " -iccProvider ./iccProvider/ic3/ > app-icctaResults.txt";

    @Before
    public void setUp(){
        droid = new IcctaTool(APKPATH, SDK);
        droid.setHomeDir(HOMEDIR);

    }

    @Test
    public void getCommand() {
        assertEquals(COMMAND, droid.getCommand());
    }

    @Test
    public void setCommand() {
        ITool droids = new IcctaTool(APKPATH, "androidSdk");
        String command = "java -jar IccTA.jar " + APKPATH + " " + "androidSdk" +
                " -iccProvider ./iccProvider/ic3/ > app-icctaResults.txt";
        assertEquals(command, droids.getCommand());

    }

    @Test
    public void getName(){
        String name = droid.getToolName();
        assertEquals("iccta", name);
    }

}