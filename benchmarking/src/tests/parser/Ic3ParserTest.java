package parser;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import leaks.Leak;
import org.junit.Test;
import org.junit.runner.RunWith;
import tool.ITool;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JExample.class)
public class Ic3ParserTest {

    private IParser parser;
    private String testFilePath = System.getProperty("user.dir") + "/testSources/ic3TestResults.txt";
    private File file = new File(testFilePath);

    private ArrayList<Leak> leaks = new ArrayList<>();
    private ITool tool = mock(ITool.class);

    @Test
    public void findingsSize() {
        parser = new IC3Parser(file);
        leaks = parser.getLeaks(tool);
        assertEquals(3, leaks.size());
    }

    /****************************************************
     * First Leak
     ***************************************************/

    @Given("findingsSize")
    public void getClassName0() {
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(0).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName0()
    {
        assertEquals("onClick(android.view.View)", leaks.get(0).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn0() {
        assertEquals("void", leaks.get(0).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod0() {
        assertEquals("startActivityForResult(android.content.Intent,int)", leaks.get(0).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn0() {
        assertEquals("void", leaks.get(0).getSinkMethodReturn());
    }

    /****************************************************
     * Second Leak
     ***************************************************/

    @Given("findingsSize")
    public void getClassName1() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(1).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName1()
    {
        assertEquals("onActivityResult(int,int,android.content.Intent)", leaks.get(1).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn1() {
        assertEquals("void", leaks.get(1).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod1() {
        assertEquals("getString(java.lang.String)", leaks.get(1).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn1() {
        assertEquals("java.lang.String", leaks.get(1).getSinkMethodReturn());

    }

    /****************************************************
     * Third Leak
     ***************************************************/

    @Given("findingsSize")
    public void getClassName2() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(2).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName2()
    {
        assertEquals("onActivityResult(int,int,android.content.Intent)", leaks.get(2).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn2() {
        assertEquals("void", leaks.get(2).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod2() {
        assertEquals("getString(java.lang.String)", leaks.get(2).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn2() {
        assertEquals("java.lang.String", leaks.get(2).getSinkMethodReturn());
    }

}