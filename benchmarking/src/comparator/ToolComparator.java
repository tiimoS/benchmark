package comparator;

import leaks.Leak;
import tool.ITool;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class ToolComparator {

    private ArrayList<ITool> tools;
    private ArrayList<Leak> leaks;
    private int[][] absMatchingMatrix;
    private double[][] relMatchingMatrix;
    private int numberOfLeaks;
    private int size;

    public ToolComparator(ArrayList<Leak> leaks, ArrayList<ITool> tools){
        assert tools.size() > 0;
        this.tools = tools;
        this.leaks = leaks;
        this.numberOfLeaks = leaks.size();
        this.size = tools.size();
        this.absMatchingMatrix = new int[size][size];
        this.relMatchingMatrix = new double[size][size];
    }


    public String getMatrix(){
        getAbsMatchingMatrix();
        getRelMatchingMatrix();
        return this.toString();
    }

    public int[][] getAbsMatchingMatrix(){
        for(Leak leak : leaks){
            int[][] leakMatrix = createAbsMatchingMatrix(leak);
            for(int i = 0; i < size; i++){
                for(int j = 0; j <= i ; j++){
                    absMatchingMatrix[i][j] += leakMatrix[i][j];
                }
            }

        }
        return absMatchingMatrix;
    }

    public double[][] getRelMatchingMatrix(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j <= i; j++){
                relMatchingMatrix[i][j] = (double) absMatchingMatrix[i][j] / numberOfLeaks * 100;
            }
        }
        return relMatchingMatrix;
    }

    protected int[][] createAbsMatchingMatrix(Leak leak){
        ArrayList<ITool> leakTools = leak.getTools();
        int[][] leakMatrix = new int[size][size];
        for(int i = 0; i < leakTools.size(); i++){
            for(int j = i; j < leakTools.size(); j++){
                int tool1Index = leakTools.get(i).getMatrixIndex();
                int tool2Index = leakTools.get(j).getMatrixIndex();
                if(tool1Index < tool2Index){
                    leakMatrix[tool2Index][tool1Index]++;
                } else {leakMatrix[tool1Index][tool2Index]++;}
            }
        }
        return leakMatrix;
    }

    @Override
    public String toString(){
        StringBuilder toolsAxis = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        builder.append("ABSOLUTE MATCHING MATRIX FOR: ").append(numberOfLeaks).append(" LEAKS\n");
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                builder.append(absMatchingMatrix[i][j]).append("\t");
            }
            for(ITool tool: tools){
                if(tool.getMatrixIndex() == i){
                    builder.append(tool.getToolName()).append("\t");
                    toolsAxis.append(tool.getToolName().subSequence(0, 3)).append("\t");
                }
            }
            builder.append("\n");
        }
        builder.append(toolsAxis.toString());
        StringBuilder toolAxisRel = new StringBuilder();
        builder.append("\n\n").append("RELATIVE MATCHING MATRIX FOR: ").append(numberOfLeaks).append(" LEAKS\n");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                double val = relMatchingMatrix[i][j];
                builder.append(formatter.format(val)).append("%").append("\t");
            }
            for(ITool tool: tools){
                if(tool.getMatrixIndex() == i){
                    builder.append(tool.getToolName()).append("\t");
                    toolAxisRel.append(tool.getToolName().subSequence(0, 3)).append("\t");
                }
            }
            builder.append("\n");
        }
        builder.append(toolAxisRel.toString());

        return builder.toString();
    }


}
