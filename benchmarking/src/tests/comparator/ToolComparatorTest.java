package comparator;


import leaks.Leak;
import org.junit.Test;
import org.junit.Before;
import tool.*;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToolComparatorTest {
    private ArrayList<ITool> leakTools = new ArrayList<>();
    private ArrayList<ITool> tools = new ArrayList<>();
    private ArrayList<Leak> leaks = new ArrayList<>();
    private ITool flowdroid;
    private ITool horndroid;
    private ITool iccta;
    private ITool covert;
    private ITool ic3;
    private ToolComparator comparator;

    @Before
    public void setUp() {
        flowdroid = mock(FlowDroidTool.class);
        when(flowdroid.getMatrixIndex()).thenReturn(0);
        when(flowdroid.getToolName()).thenReturn("flowdroid");

        horndroid = mock(HorndroidTool.class);
        when(horndroid.getMatrixIndex()).thenReturn(1);
        when(horndroid.getToolName()).thenReturn("horndroid");

        iccta = mock(IcctaTool.class);
        when(iccta.getMatrixIndex()).thenReturn(2);
        when(iccta.getToolName()).thenReturn("iccta");

        covert = mock(CovertTool.class);
        when(covert.getMatrixIndex()).thenReturn(3);
        when(covert.getToolName()).thenReturn("covert");

        ic3 = mock(Ic3Tool.class);
        when(ic3.getMatrixIndex()).thenReturn(4);
        when(ic3.getToolName()).thenReturn("ic3");

        tools.clear();
        tools.add(flowdroid);
        tools.add(horndroid);
        tools.add(iccta);
        tools.add(covert);
        tools.add(ic3);

        Leak leak1 = mock(Leak.class);
        when(leak1.getTools()).thenReturn(leakTools);
        leaks.clear();
        leaks.add(leak1);

        leakTools.clear();
    }

    @Test
    public void createAbsMatchingMatrixOneTool() {
        ToolComparator comparator = new ToolComparator(leaks, tools);
        leakTools.add(flowdroid);
        int index = flowdroid.getMatrixIndex();
        int[][] matrix = comparator.createAbsMatchingMatrix(leaks.get(0));
        assertEquals(1, matrix[index][index]);

        for(int i = 1; i < tools.size(); i++){
            for(int j = 0; j < tools.size(); j++){
                assertEquals(0, matrix[i][j]);
            }
        }

        for(int j = 1; j < tools.size(); j++){
            assertEquals(0, matrix[0][j]);
        }
    }

    @Test
    public void createAbsMatchingMatrixTwoTools() {
        ToolComparator comparator = new ToolComparator(leaks, tools);
        leakTools.add(flowdroid);
        leakTools.add(iccta);
        int index1 = flowdroid.getMatrixIndex();
        int index2 = iccta.getMatrixIndex();
        int[][] matrix = comparator.createAbsMatchingMatrix(leaks.get(0));
        assertEquals(1, matrix[index1][index1]);
        assertEquals(1, matrix[index2][index2]);
        assertEquals(1, matrix[index2][index1]);

        for(int i = 1; i < tools.size(); i++){
            assertEquals(0, matrix[0][i]);
        }

        for(int i = 0; i < tools.size(); i++){
            assertEquals(0, matrix[1][i]);
        }

        assertEquals(0, matrix[2][1]);
        for(int i = 4; i < tools.size(); i++){
            assertEquals(0, matrix[2][i]);
        }

        for(int i = 0; i < tools.size(); i++){
            assertEquals(0, matrix[3][i]);
        }

        for(int i = 0; i < tools.size(); i++){
            assertEquals(0, matrix[4][i]);
        }
    }

    @Test
    public void createAbsMatchingMatrixThreeTools() {
        ToolComparator comparator = new ToolComparator(leaks, tools);
        leakTools.add(flowdroid);
        leakTools.add(iccta);
        leakTools.add(horndroid);
        int index1 = flowdroid.getMatrixIndex();
        int index2 = horndroid.getMatrixIndex();
        int index3 = iccta.getMatrixIndex();
        int[][] matrix = comparator.createAbsMatchingMatrix(leaks.get(0));
        assertEquals(1, matrix[index1][index1]);
        assertEquals(1, matrix[index2][index2]);
        assertEquals(1, matrix[index3][index3]);

        for(int i = 0; i < tools.size(); i++){
            assertEquals(0, matrix[3][i]);
            assertEquals(0, matrix[4][i]);
        }

        for(int i = 0; i < 3; i++){
            assertEquals(1, matrix[2][i]);
        }

        for(int i = 0; i < 2; i++){
            assertEquals(1, matrix[1][i]);
        }

        for(int i = 1; i < tools.size(); i++){
            assertEquals(0, matrix[0][i]);
        }

        for(int i = 2; i < tools.size(); i++){
            assertEquals(0, matrix[1][i]);
        }

        for(int i = 3; i < tools.size(); i++){
            assertEquals(0, matrix[2][i]);
        }
    }


    @Test
    public void getMatchingMatrix(){
        ArrayList<ITool> tools1 = new ArrayList<>();
        tools1.add(flowdroid);

        ArrayList<ITool> tools2 = new ArrayList<>();
        tools2.add(flowdroid);
        tools2.add(horndroid);

        ArrayList<ITool> tools3 = new ArrayList<>();
        tools3.add(horndroid);
        tools3.add(iccta);
        tools3.add(flowdroid);

        Leak leak1 = mock(Leak.class);
        when(leak1.getTools()).thenReturn(tools1);

        Leak leak2 = mock(Leak.class);
        when(leak2.getTools()).thenReturn(tools2);

        Leak leak3 = mock(Leak.class);
        when(leak3.getTools()).thenReturn(tools3);

        ArrayList<Leak> leaks = new ArrayList<>();
        leaks.add(leak1);
        leaks.add(leak2);
        leaks.add(leak3);

        comparator = new ToolComparator(leaks, tools);
        int[][] matrix = comparator.getAbsMatchingMatrix();

        assertEquals(3, matrix[0][0]);

        for(int i = 1; i < tools.size(); i++){
            assertEquals(0, matrix[0][i]);
        }


        for(int i = 0; i < 2; i++){
            assertEquals(2, matrix[1][i]);
        }

        for(int i = 2; i < tools.size(); i++){
            assertEquals(0, matrix[1][i]);
        }

        for(int i = 0; i < 3; i++){
            assertEquals(1, matrix[2][i]);
        }

        for(int i = 4; i < tools.size(); i++){
            assertEquals(0, matrix[2][i]);
        }

        for(int i = 3; i < tools.size(); i++){
            for(int j= 0; j < tools.size(); j++){
                assertEquals(0, matrix[i][j]);
            }
        }

    }

    @Test
    public void getRelMatchingMatrix(){
        getMatchingMatrix();
        comparator.getRelMatchingMatrix();
        System.out.println(comparator.toString());
    }




}