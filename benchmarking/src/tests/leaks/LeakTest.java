package leaks;

import org.junit.Before;
import org.junit.Test;
import tool.FlowDroidTool;
import tool.ITool;
import tool.Tool;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeakTest {
    private Leak leak1;
    private Leak leak2;
    private Leak leak3;
    private ITool tool = mock(FlowDroidTool.class);

   @Before
    public void setUp(){
       tool = mock(ITool.class);
       when(tool.getToolName()).thenReturn("flowdroid");
       leak1 = setupLeak("class2", "method2", "", "sink1", "void");
       leak2 = setupLeak("class1", "method1", "void","sink1", "void");
       leak3 = setupLeak("class1", "method1", "", "sink1", "");
    }

    @Test
    public void compareTo() {
        assertEquals(1, leak1.compareTo(leak2));
        assertEquals(0, leak2.compareTo(leak3));

    }

    @Test
    public void toStringRepresentation() {
        String representation = "Class Name: class2\nMethod Name: method2 \nSink Method: sink1 void\nFound by Tool: [flowdroid]\n";
        assertEquals(representation, leak1.toString());
    }


    @Test
    public void getClassName() {
        assertEquals("class1", leak2.getClassName());

    }

    @Test
    public void getMethodName() {
        assertEquals("method2", leak1.getMethodName());

    }

    @Test
    public void getToolName() {
        assertEquals("flowdroid", leak1.getToolName());
    }

    @Test
    public void getToolNameList() {
        assertEquals("[flowdroid]", leak1.getToolNameList().toString());
    }

    @Test
    public void addTool() {
        leak1.addTool("tool2");
        assertEquals("[flowdroid, tool2]", leak1.getToolNameList().toString());
    }

    @Test
    public void enhanceFields(){
       leak3.enhanceFields(leak2);
       assertEquals("void", leak3.getMethodReturn());
       assertEquals("[flowdroid]", leak3.getToolNameList().toString());
    }


    public Leak setupLeak(String className, String method, String methodReturn, String sink, String sinkReturn){
        ArrayList<String> character = new ArrayList<>();
        character.add(className);
        character.add(method);
        character.add(methodReturn);
        character.add(sink);
        character.add(sinkReturn);
        Leak leak = new Leak(character, tool);
        return leak;
    }
}