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
public class IcctaParserTest {

    private IParser parser;
    private String testFilePath = System.getProperty("user.dir") + "/testSources/icctaTestResults.txt";
    private File file = new File(testFilePath);
    private ArrayList<Leak> leaks = new ArrayList<>();
    private ITool tool = mock(ITool.class);


    @Test
    public void findingsSize() {
        when(tool.getToolName()).thenReturn("iccta");
        parser = new IcctaParser(file);
        leaks = parser.getLeaks(tool);
        assertEquals(7, leaks.size());

    }

    /**
     * ***********************************************
     * First Leak
     ***************************************************/

    @Given("findingsSize")
    public void getClassName0() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(0).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName0() {
        assertEquals("sendSMSMessage(java.lang.String)", leaks.get(0).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn0() {
        assertEquals("void", leaks.get(0).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod0() {
        assertEquals("sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)", leaks.get(0).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn0() {
        assertEquals("void", leaks.get(0).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName0() {
        assertEquals("iccta", leaks.get(0).getToolName());
    }

    /**
     * ***********************************************
     * Second Leak
     ***************************************************/


    @Given("findingsSize")
    public void getClassName1() {
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(1).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName1() {
        assertEquals("onClick(android.view.View)", leaks.get(1).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn1() {
        assertEquals("void", leaks.get(1).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod1() {
        assertEquals("startActivityForResult(android.content.Intent,int)", leaks.get(1).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn1() {
        assertEquals("void", leaks.get(1).getSinkMethodReturn());
    }

    /**
     * ***********************************************
     * Third Leak
     ***************************************************/


    @Given("findingsSize")
    public void getClassName2() {
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(2).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName2() {
        assertEquals("onClick(android.view.View)", leaks.get(2).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn2() {
        assertEquals("void", leaks.get(2).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod2() {
        assertEquals("i(java.lang.String,java.lang.String)", leaks.get(2).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn2() {
        assertEquals("int", leaks.get(2).getSinkMethodReturn());
    }

    /**************************************************
     * Fourth Leak
     ***************************************************/
    @Given("findingsSize")
    public void getClassName3() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(3).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName3()
    {
        assertEquals("sendSMSMessage(java.lang.String)", leaks.get(3).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn3()
    {
        assertEquals("void", leaks.get(3).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod3() {
        assertEquals("sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)", leaks.get(3).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn3() {
        assertEquals("void", leaks.get(3).getSinkMethodReturn());
    }

    /**************************************************
     * Fifth Leak
     ***************************************************/
    @Given("findingsSize")
    public void getClassName4(){
        assertEquals("edu.mit.shared_preferences.AnotherActivity", leaks.get(4).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName4()
    {
        assertEquals("onCreate(android.os.Bundle)", leaks.get(4).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn4()
    {
        assertEquals("void", leaks.get(4).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod4() {
        assertEquals("i(java.lang.String,java.lang.String)", leaks.get(4).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn4() {
        assertEquals("int", leaks.get(4).getSinkMethodReturn());
    }

    /**************************************************
     * 6th Leak
     ***************************************************/
    @Given("findingsSize")
    public void getClassName5(){
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(5).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName5()
    {
        assertEquals("onClick(android.view.View)", leaks.get(5).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn5()
    {
        assertEquals("void", leaks.get(5).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod5() {
        assertEquals("startActivityForResult(android.content.Intent,int)", leaks.get(5).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn5() {
        assertEquals("void", leaks.get(5).getSinkMethodReturn());
    }

    /**************************************************
     * 7th Leak
     ***************************************************/
    @Given("findingsSize")
    public void getClassName6(){
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(6).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName6()
    {
        assertEquals("onClick(android.view.View)", leaks.get(6).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn6()
    {
        assertEquals("void", leaks.get(6).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod6() {
        assertEquals("i(java.lang.String,java.lang.String)", leaks.get(6).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn6() {
        assertEquals("int", leaks.get(6).getSinkMethodReturn());
    }

}