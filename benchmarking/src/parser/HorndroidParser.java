package parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Responsible for parsing horndroid tool results files and returning leaks that are indicated inside this results file (.log)
 * Defines the regex required to find the desired components inside the results file such as leak description, class toolName,
 * method toolName etc. Furthermore, it overrides the method to find the method toolName inside the leak description.
 *
 * It extends the Parsers class that implements the IParser Interface.
 *
 * @author Timo Spring
 */
public class HorndroidParser extends Parsers {
    private ArrayList<String> leaksFromReport;


    public HorndroidParser(File file) {
        super(file);
        setLeakTag("((Test if register )|(\\[REF\\] Test if register )).*(?=:POTENTIAL LEAK)");
        setCutAwayTag("");
        setClassNameRegex("(?<=of the class L).*(?=; to the sink)");
        setFullMethodRegex("(?<=method )\\w+\\((\\w?|\\;|\\/)+\\)");
        setSinkMethodRegex("(?<=to the sink ).*\\)");
        pattern = Pattern.compile(getLeakTag());

    }

    @Override
    public void readFile(File file){
        this.leaksFromReport = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonFile = (JSONObject) parser.parse(new FileReader(file.getPath()));
            JSONArray leaks = (JSONArray) jsonFile.get("reportEntries");
            for (Object obj : leaks){
                StringBuilder builder = new StringBuilder();
                JSONObject leak = (JSONObject) obj;
                String leakDescription = (String) leak.get("description");
                String result = (String) leak.get("result");
                if(result.equals("POTENTIAL LEAK")){
                    builder.append(leakDescription).append(" ").append(result).append("\n");
                    leaksFromReport.add(builder.toString());
                }
            }


        } catch (IOException e) {
            System.out.println("Error: File " + file.getName() + " could not be read." + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Error: File " + file.getName() + " could not be parsed." + e.getMessage());
        }
    }

    @Override
    protected ArrayList<String> findLeaksInReport(){
        assert leaksFromReport != null;
        return leaksFromReport;
    }

    /**
     * Finds the class toolName inside the leak description and brings it to a similar format as the other tools use i.e.
     * with "." as a delimiter instead of a "/".
     *
     * @param finding - String description of the leak.
     * @return class Name as a string
     */
    @Override
    protected String findClassName(String finding){
        String className = findMatches(finding, getClassNameRegex());
        className = removeAnnotations(className);
        return className;
    }

    /**
     * Finds the method toolName inside the leak description and brings it to a similar format as the other tools use i.e.
     * with "." as a delimiter instead of a "/".
     *
     * @param finding - String description of the leak.
     * @return method Name as a string
     */
    @Override
    protected String[] findMethodName(String finding){
        String fullMethodName = removeAnnotations(findMatches(finding, getFullMethodRegex()));
        String[] method = {fullMethodName, ""};
        return method;
    }

    @Override
    protected String[] findSinkMethod(String finding){
        String methodName = removeAnnotations(findMatches(finding, getSinkMethodRegex()));
        String[] method = {methodName, ""};
        return method;
    }

    private String removeAnnotations(String finding){
        finding = finding.replaceAll("Ljava", "java");
        finding = finding.replaceAll("Landroid", "android");
        finding = finding.replaceAll("\\/", ".");
        finding = finding.replaceAll("\\(L", "(");
        finding = finding.replaceAll("\\;L", ";");
        finding = finding.replaceAll("\\;\\)", ")");
        finding = finding.replaceAll("\\;", ",");
        finding = finding.replaceAll("II", "");
        return finding;
    }


}

