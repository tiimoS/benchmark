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
public class HorndroidParserTest {


    private IParser parser;
    private String testFilePath = System.getProperty("user.dir") + "/testSources/horndroidTestResults.json";
    private File file = new File(testFilePath);
    private ArrayList<Leak> leaks = new ArrayList<>();
    private ITool tool = mock(ITool.class);

    @Test
    public void findingsSize() {
        when(tool.getToolName()).thenReturn("horndroid");
        parser = new HorndroidParser(file);
        leaks = parser.getLeaks(tool);

        assertEquals(14, leaks.size());
    }

    /*****************************************************************
     * First Leak
     ****************************************************************/

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
        assertEquals("", leaks.get(0).getMethodReturn());
    }


    @Given("findingsSize")
    public void getSinkMethod0() {
        assertEquals("i(java.lang.String,java.lang.String)", leaks.get(0).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn0() {
        assertEquals("", leaks.get(0).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName0(){
        assertEquals("horndroid", leaks.get(0).getToolName());
    }

    /*****************************************************************
     * Second Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName1() {
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(1).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName1()
    {
        assertEquals("onClick(android.view.View)", leaks.get(1).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn1() {
        assertEquals("", leaks.get(1).getMethodReturn());
    }


    @Given("findingsSize")
    public void getSinkMethod1() {
        assertEquals("i(java.lang.String,java.lang.String)", leaks.get(1).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn1() {
        assertEquals("", leaks.get(1).getSinkMethodReturn());
    }


    @Given("findingsSize")
    public void getToolName1(){
        assertEquals("horndroid", leaks.get(1).getToolName());
    }

    /*****************************************************************
     * Third Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName2() {
        assertEquals("org.cert.sendsms.Button1Listener", leaks.get(2).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName2()
    {
        assertEquals("onClick(android.view.View)", leaks.get(2).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn2() {
        assertEquals("", leaks.get(2).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod2() {
        assertEquals("i(java.lang.String,java.lang.String)", leaks.get(2).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn2() {
        assertEquals("", leaks.get(2).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName2(){
        assertEquals("horndroid", leaks.get(2).getToolName());
    }

    /*****************************************************************
     * Fourth Leak
     ****************************************************************/

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
    public void getMethodReturn3() {
        assertEquals("", leaks.get(3).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod3() {
        assertEquals("sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)", leaks.get(3).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn3() {
        assertEquals("", leaks.get(3).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName3(){
        assertEquals("horndroid", leaks.get(3).getToolName());
    }

    /*****************************************************************
     * 5th Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName4() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(4).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName4()
    {
        assertEquals("sendSMSMessage(java.lang.String)", leaks.get(4).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn4() {
        assertEquals("", leaks.get(4).getMethodReturn());
    }



    @Given("findingsSize")
    public void getSinkMethod4() {
        assertEquals("sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)", leaks.get(3).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn4() {
        assertEquals("", leaks.get(4).getSinkMethodReturn());
    }


    @Given("findingsSize")
    public void getToolName4(){
        assertEquals("horndroid", leaks.get(4).getToolName());
    }

    /*****************************************************************
     * 6th Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName5() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(5).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName5()
    {
        assertEquals("sendSMSMessage(java.lang.String)", leaks.get(5).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn5() {
        assertEquals("", leaks.get(5).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod5() {
        assertEquals("sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)", leaks.get(5).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn5() {
        assertEquals("", leaks.get(5).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName5(){
        assertEquals("horndroid", leaks.get(5).getToolName());
    }

    /*****************************************************************
     * 7th Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName6() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(6).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName6()
    {
        assertEquals("sendSMSMessage(java.lang.String)", leaks.get(6).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn6() {
        assertEquals("", leaks.get(6).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod6() {
        assertEquals("sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)", leaks.get(6).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn6() {
        assertEquals("", leaks.get(6).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName6(){
        assertEquals("horndroid", leaks.get(6).getToolName());
    }

    /*****************************************************************
     * 8th Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName7() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(7).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName7()
    {
        assertEquals("onActivityResult(android.content.Intent)", leaks.get(7).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn7() {
        assertEquals("", leaks.get(7).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod7() {
        assertEquals("v(java.lang.String,java.lang.String)", leaks.get(7).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn7() {
        assertEquals("", leaks.get(7).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName7(){
        assertEquals("horndroid", leaks.get(7).getToolName());
    }


    /*****************************************************************
     * 9th Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName8() {
        assertEquals("org.cert.sendsms.MainActivity", leaks.get(7).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName8()
    {
        assertEquals("onActivityResult(android.content.Intent)", leaks.get(8).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn8() {
        assertEquals("", leaks.get(8).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod8() {
        assertEquals("v(java.lang.String,java.lang.String)", leaks.get(8).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn8() {
        assertEquals("", leaks.get(8).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName8(){
        assertEquals("horndroid", leaks.get(8).getToolName());
    }


    /*****************************************************************
     * 14th Leak
     ****************************************************************/

    @Given("findingsSize")
    public void getClassName14()
    {
        assertEquals("org.cert.WriteFile.Button1Listener", leaks.get(13).getClassName());
    }

    @Given("findingsSize")
    public void getMethodName14()
    {
        assertEquals("getMyLocation()", leaks.get(13).getMethodName());
    }

    @Given("findingsSize")
    public void getMethodReturn14()
    {
        assertEquals("", leaks.get(13).getMethodReturn());
    }

    @Given("findingsSize")
    public void getSinkMethod14() {
        assertEquals("valueOf(D)", leaks.get(13).getSinkMethod());
    }

    @Given("findingsSize")
    public void getSinkMethodReturn14() {
        assertEquals("", leaks.get(13).getSinkMethodReturn());
    }

    @Given("findingsSize")
    public void getToolName14(){
        assertEquals("horndroid", leaks.get(13).getToolName());
    }
}