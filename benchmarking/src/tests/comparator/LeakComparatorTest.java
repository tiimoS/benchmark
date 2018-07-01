package comparator;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import leaks.Leak;
import org.junit.Test;
import org.junit.runner.RunWith;
import tool.ITool;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JExample.class)
public class LeakComparatorTest {

    private LeakComparator leakComparator = new LeakComparator();
    private Leak leak1;
    private Leak leak2;
    private Leak leak3;
    private Leak leak4;
    private ITool tool;
    private ArrayList<Leak> leaks = new ArrayList<>();
    private ArrayList<Leak> groupedLeaks = new ArrayList();


    @Test
    public void groupSameLeaks(){
        setups();
        groupedLeaks = leakComparator.groupLeaks(leaks);
        assertEquals(3, groupedLeaks.size());
        assertEquals(leak3, groupedLeaks.get(0));
        assertEquals(leak2, groupedLeaks.get(1));
        assertEquals(leak1, groupedLeaks.get(2));

    }

    @Given("groupSameLeaks")
    public void enhancedGroupedLeaks(){
        assertEquals("void", leak2.getMethodReturn());
        assertEquals("[flowdroid]", leak2.getToolNameList().toString());
     ;
    }

    @Given("groupSameLeaks")
    public void sortLeaks() {
        assertEquals(groupedLeaks.get(0), leak3);
        assertEquals(groupedLeaks.get(1), leak2);
        assertEquals(groupedLeaks.get(2), leak1);
    }


    public void setups(){
        tool = mock(ITool.class);
        when(tool.getToolName()).thenReturn("flowdroid");
        leak1 = setupLeak("class2", "method2", "", "sink1", "void");
        leak2 = setupLeak("class2", "method1", "void","sink1", "void");
        leak3 = setupLeak("class1", "method1", "", "sink1", "");
        leak4 = setupLeak("class2", "method2", "void","sink1", "");
        leaks.add(leak1);
        leaks.add(leak2);
        leaks.add(leak3);
        leaks.add(leak4);
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