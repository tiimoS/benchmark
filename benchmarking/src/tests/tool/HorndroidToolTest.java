package tool;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Responsible for Testing the HornDroidTool properties i.e. its specific implementation of the Tools class.
 *
 * @author Timo Spring
 */
public class HorndroidToolTest {
    private ITool droid;
    private static final String HOMEDIR = "home";
    private static final String APKPATH = "apk/app.apk";
    private static final String SDK = "sdk";
    private static final String COMMAND = "java -jar fshorndroid-0.0.1.jar / ./apktool.jar " + APKPATH;

    @Before
    public void setUp(){
        droid = new HorndroidTool(APKPATH, SDK);
        droid.setHomeDir(HOMEDIR);

    }

    @Test
    public void getCommand() {
        assertEquals(COMMAND, droid.getCommand());
    }

    @Test
    public void setCommand() {
        ITool droids = new HorndroidTool(APKPATH, "sdk");
        String command = "java -jar fshorndroid-0.0.1.jar / ./apktool.jar apk/app.apk";
        assertEquals(command, droids.getCommand());

    }

    @Test
    public void cleanToolOuput(){
        //TODO add testcase for cleaning output
    }

    @Test
    public void getName(){
        String name = droid.getToolName();
        assertEquals("horndroid", name);
    }

    @Test
    public void parseError(){
        String line = "<android.app.ContextImpl: android.content.Intent registerReceiver(android.content.BroadcastReceiver,android.content.IntentFilter,java.lang.String,android.os.Handler,int)> -> _SINK_";
        String line2 = "<android.graphics.drawable.GradientDrawable$GradientState: void setCornerRadius(float)> -> _SINK_";
        String line3 = "<android.net.lowpan.ILowpanInterfaceListener$Stub$Proxy: void onLinkAddressRemoved(java.lang.String)> -> _SINK_";

        parse(line);
        parse(line2);
        parse(line3);
    }


    private static void parse(String line) {
        String[] parts = null, parts2 = null;
        String className = null, methodName = null;
        parts = line.split(Pattern.quote("> -> _S"));
        parts2 = parts[0].split(Pattern.quote(": "));
        className = parts2[0].substring(1);
        methodName = parts2[1].split(" ")[1];
        System.out.println(className + " " + methodName + "\n");
    }

}