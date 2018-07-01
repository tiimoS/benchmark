package leaks;


import tool.ITool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Responsible for defining the characteristics of a Leak object such as component toolName, class toolName and method toolName.
 * We parse the results file of each tool to get a description of each leak. We then search for those characteristics
 * inside the found leak descriptions and create the corresponding Leak objects to it. So the Leak Objects are created
 * by the Parsers and used later on for comparing found leaks among the different tools.
 *
 * @author Timo Spring
 */
public class Leak implements Comparable<Leak>{
    private String appName;
    private String className;
    private String methodName;
    private String methodReturn;
    private String sinkMethod;
    private String sinkMethodReturn;
    private ArrayList<String> toolName;
    private ArrayList<ITool> tools ;
    private int[] csvLeakCounter = new int[5];

   public Leak(){}

    public Leak(ArrayList<String> properties, ITool tool){
        this.toolName = new ArrayList();
        this.tools = new ArrayList<>();
        toolName.add(tool.getToolName());
        appName = tool.getAppName();
        tools.add(tool);
        csvLeakCounter[tool.getMatrixIndex()] += 1;
        updateLeak(properties);
    }

    private void updateLeak(ArrayList<String> properties){
        this.className = properties.get(0);
        this.methodName = properties.get(1);
        this.methodReturn = properties.get(2);
        this.sinkMethod = properties.get(3);
        this.sinkMethodReturn = properties.get(4);
    }

    public void enhanceFields(Leak nextLeak) {
        if (this.getMethodReturn().equals("")){
           this.methodReturn = nextLeak.getMethodReturn();
        }

        if (this.getSinkMethodReturn().equals("")){
            this.sinkMethodReturn = nextLeak.getSinkMethodReturn();
        }

        List<ITool> toolsList = nextLeak.getTools();
        for(ITool tool: toolsList){
            if(!toolName.contains(tool.getToolName())) {
                toolName.add(tool.getToolName());
                tools.add(tool);
                csvLeakCounter[tool.getMatrixIndex()]+=1;
            }
        }
    }

    @Override
    public int compareTo(Leak leak){
        return Comparator.comparing(Leak::getClassName)
                .thenComparing(Leak::getMethodName)
                .thenComparing(Leak::getSinkMethod)
                .compare(this, leak);
    }


    /**
     * Returns a string representation of the Leak object containing the component toolName, class toolName and method toolName of
     * the described leak.
     * @return String representation of the leak.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Class Name: ").append(className).append("\n");
        builder.append("Method Name: ").append(methodName).append(" ").append(methodReturn).append("\n");
        builder.append("Sink Method: ").append(sinkMethod).append(" ").append(sinkMethodReturn).append("\n");
        builder.append("Found by Tool: ").append(toolName.toString()).append("\n");
        return builder.toString();
    }

    public String getCSVRepresentation(String delimiter){
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append(appName).append(delimiter);
        builder.append(className).append(delimiter);
        builder.append(methodName).append(delimiter).append(methodReturn).append(delimiter);
        builder.append(sinkMethod).append(delimiter).append(sinkMethodReturn).append(delimiter);
        for(int i = 0; i < 5; i++){
            builder.append(csvLeakCounter[i]).append(delimiter);
        }
        builder.append(getNumberOfMatches()).append(delimiter);
        return builder.toString();
    }

    /** ----------------------------------------------------------------------------------------------------------------
     * HELPER METHODS (GETTERS & SETTERS)
     * ----------------------------------------------------------------------------------------------------------------*/

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodReturn(){
        return methodReturn;
    }

    public String getSinkMethod(){
        return this.sinkMethod;
    }

    public String getSinkMethodReturn(){
        return this.sinkMethodReturn;
    }

    public String getToolName(){
        return toolName.get(0);
    }

    public List<String> getToolNameList() {
        return this.toolName;
    }

    public ArrayList<ITool> getTools() {
        return this.tools;
    }

    public String getNumberOfMatches(){
        return Integer.toString(toolName.size());
    }

    public void addTool(String newToolName) {
        toolName.add(newToolName);
        java.util.Collections.sort(toolName);

    }
}
