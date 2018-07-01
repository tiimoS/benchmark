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
import static org.mockito.Mockito.when;

@RunWith(JExample.class)
public class CovertParserTest {

    private IParser parser;
    private String testFilePath = System.getProperty("user.dir") + "/testSources/covertTestResults.xml";
    private File file = new File(testFilePath);
    private ArrayList<Leak> leaks = new ArrayList<>();
    private ITool tool = mock(ITool.class);

    @Test
    public void findingsSize() {
        parser = new CovertParser(file);
        when(tool.getToolName()).thenReturn("covert");
        leaks = parser.getLeaks(tool);
        assertEquals(2, leaks.size());
    }

    /** *******************************************
     * First Leak
     **********************************************/


    @Given("findingsSize")
    public void getClassName0() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(0).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName0() {
        assertEquals("onActivityResult(int,int,android.content.Intent)", leaks.get(0).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn0(){
        assertEquals("void", leaks.get(0).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod0(){
        assertEquals("onActivityResult(int,int,android.content.Intent)", leaks.get(0).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn0(){
        assertEquals("void", leaks.get(0).getMethodReturn());
    }


    /** *******************************************
     * Second Leak
     **********************************************/


    @Given("findingsSize")
    public void getClassName1() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(1).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName1() {
        assertEquals("onActivityResult(int,int,android.content.Intent)", leaks.get(1).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn1(){
        assertEquals("void", leaks.get(1).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod1(){
        assertEquals("onActivityResult(int,int,android.content.Intent)", leaks.get(1).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn1(){
        assertEquals("void", leaks.get(1).getMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName(){
        assertEquals("covert", leaks.get(0).getToolName());
    }

}