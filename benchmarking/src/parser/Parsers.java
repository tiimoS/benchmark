package parser;

import leaks.Leak;
import tool.ITool;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for implementing the parser to parse the different results files. First it reads the results file. Since
 * most of the results files also contain stack traces and not only the found leaks, we have to parse the file given a
 * leakTag indicating the start of the leak description. After we have a list of all leaks mentioned in the file,
 * we parse for Component Name, class toolName and method toolName that are indicated in the leak description. After gathering
 * all required information, we create a new Leak object with the given characteristics, so we can easily compare
 * the leaks later on.
 *
 * The Parsers class implements the IParser interface and is extended by the different tool parsers, which basically only
 * set the regex differently. The Parsers class uses Matcher, Pattern and regex.
 *
 * @author Timo Spring
 */
public class Parsers implements IParser {
    protected Pattern pattern;
    private Matcher matcher;

    protected ArrayList<String> fileAsLines;      // String for each line of the results file
    private String fileAsSingleLine;            // single line representation of the read file
    private ArrayList<String> leaksFromReport;  // found leak descriptions inside the results file, one leak for each String

    private String leakTag;                     // regex defining how leak description can be found in results file
    private String cutAwayTag;                  // regex defining if some additional info can be cut away at the end of the results file
    private String classNameRegex;              // regex defining how to find the class toolName inside the leak description
    private String fullMethodRegex;             // regex defining how to find full method toolName including return type
    private String methodNameRegex;             // regex defining how to find the method toolName inside the leak description
    private String methodReturnRegex;           // regex defining how to find the method return type inside the full method toolName
    private String sinkMethodRegex;             // regex defining how to find the leaking method (sink)

    Parsers(File file){
        assert file.isFile();
        readFile(file);
        this.leaksFromReport = new ArrayList<>();
    }

    /**
     * Reads the results file to which the parser is setup. It creates an ArrayList of Strings with a String for each line
     * in the file and a String containing the file as a single line. This is necessary since the tool parsers sometimes
     * use one or the other (since their report structure is different).
     *
     * @param file - File: results file containing the leak descriptions. either .txt, .xml or .log files
     */
    public void readFile(File file){
        fileAsLines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine())!= null){
                fileAsLines.add(line);
            }
            fileAsSingleLine = linesToSingleLine(fileAsLines);
        } catch (IOException e) {
            System.out.println("Error: File " + file.getName() + " could not be read." + e.getMessage());
        }
    }

    /**
     * Parses the results file and searches for the section where the leaks are described. Then we create a new String
     * for each leak mentioned in the results file and store it in an ArrayList of strings. We then parse each leak String
     * for component toolName, class toolName and method toolName mentioned in the description text. After we have all required information
     * we create the desired Leak Objects.
     *
     * @return ArrayList of Leak Objects containing the component toolName, class toolName and method toolName as Strings of the
     * parsed leaks.
     * @param toolName - toolName of the tool e.g. flowdroid
     */
    @Override
    public ArrayList<Leak> getLeaks(ITool tool) {
        ArrayList<String> findings = findLeaksInReport();
        return createLeaks(findings, tool);
    }


    /**
     * Finds the section in the results file, where the leaks are mentioned/described. It then creates a new String
     * in an ArrayList for each leak that is described. It is used as a preparation to later parse for component toolName
     * and the other required information.
     *
     * @return ArrayList of Strings containing a String description for each found leak in the results file.
     */
    protected ArrayList<String> findLeaksInReport() {
        matcher = pattern.matcher(fileAsSingleLine);

        if(matcher.find()){
            findLeaks(matcher.start());
        }
        return leaksFromReport;

    }

    /**
     * Find and creates substrings to given regex in a string. It does so by first searching for the start of the match and
     * saving the index of it. Then it searches for the next occurrence of the match and saves its index. Then it creates
     * a substring of the text between the current match and the next match. This is recursively repeated until there
     * are no matches found anymore.
     *
     * @param start Integer - Index of the first match found.
     */
    private void findLeaks(int start) {
        assert matcher != null && fileAsSingleLine != null && start >= 0;
        int next;
        if(matcher.find(start + leakTag.length())){
            next = matcher.start();
            leaksFromReport.add(fileAsSingleLine.substring(start, next - 1).trim());
            findLeaks(next);
        } else {
            next = fileAsSingleLine.length();
            String lastLeak = fileAsSingleLine.substring(start, next).trim();
            String[] filter = lastLeak.split(cutAwayTag);
            leaksFromReport.add(filter[0]);
        }
    }

    /**
     * Matches a regex on a given String. It does so by compiling the pattern with the new regex and then setting up the
     * matcher and returning the first match found. Is used to find Component toolName, class toolName and method toolName in the
     * string description of the leak.
     *
     * @param finding - String: Description text of the found leak. Contains the information that we are looking for e.g.
     *                component toolName
     * @param regex - String: Defining what we are looking for i.e. pattern of the desired information.
     * @return String - found match to our search e.g. Component Name.
     */
     String findMatches(String finding, String regex){
         pattern = Pattern.compile(regex);
         matcher = pattern.matcher(finding);
         if(matcher.find()){
             return matcher.group(0);
         }return "";
    }

    /**
     * Creates Leak objects out of Component Name, Class toolName and method toolName that are parsed from the leak description
     * text.
     *
     * @param findings - String: List of found leak descriptions in the results file that must be further parsed for
     *                 component toolName, class toolName and method toolName.
     * @param toolName  - String toolName of tool e.g. flowdroid
     * @return list of Leak objects
     */
    private ArrayList<Leak> createLeaks(ArrayList<String> findings, ITool tool) {
        ArrayList<Leak> leaks = new ArrayList<>();
        for(String finding: findings){
            ArrayList<String> characteristics = new ArrayList<>();
            characteristics.add(findClassName(finding));
            String[] method = findMethodName(finding);
            characteristics.add(method[0]); // method toolName
            characteristics.add(method[1]); // method return type
            String[] sinkMethod = findSinkMethod(finding);
            characteristics.add(sinkMethod[0]); // sink method toolName
            characteristics.add(sinkMethod[1]); // sink method return type

            Leak leak = new Leak(characteristics, tool);
            leaks.add(leak);
        }
        return leaks;
    }



    /**
     * Creats a single line representation to a given ArrayList of Strings.
     *
     * @param lines - ArrayList of Strings. Each String contains a line of the results file that was read previously.
     * @return String - containing the single line representation of the results file.
     */
    private String linesToSingleLine(ArrayList<String> lines) {
        assert !lines.isEmpty();
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

    /**
     * Finds the class toolName inside the leak description string. It does so by first getting the regex that defines
     * how to find the class toolName for the given tool parser and then calling the general findMatches method to
     * get the desired information.
     *
     * @param finding - String description of the leak.
     * @return String - class toolName found in the leak description
     */
     protected String findClassName(String finding) {
        return findMatches(finding, getClassNameRegex());

    }

    /**
     * Finds the method toolName inside the leak description string. It does so by first getting the regex that defines
     * how to find the method toolName for the given tool parser and then calling the general findMatches method to
     * get the desired information.
     *
     * @param finding - String description of the leak.
     * @return String - method toolName found in the leak description
     */
    protected String[] findMethodName(String finding) {
        String fullMethod = findMatches(finding, getFullMethodRegex());
        String methodReturn = findMatches(fullMethod, getMethodReturnRegex());
        String methodName = findMatches(fullMethod, getMethodNameRegex());
        String[] method = {methodName, methodReturn};
        return method;
    }

    protected String[] findSinkMethod(String finding) {
        String fullMethod = findMatches(finding, getSinkMethodRegex());
        String methodReturn = findMatches(fullMethod, getMethodReturnRegex());
        String methodName = findMatches(fullMethod, getMethodNameRegex());
        String[] method = {methodName, methodReturn};
        return method;
    }


    /** ----------------------------------------------------------------------------------------------------------------
     * HELPER METHODS (GETTERS & SETTERS)
     * ----------------------------------------------------------------------------------------------------------------*/


    void setClassNameRegex(String classNameRegex){
        this.classNameRegex = classNameRegex;
    }

    String getClassNameRegex() {
        return this.classNameRegex;
    }

    void setFullMethodRegex(String fullMethodRegex){
        this.fullMethodRegex = fullMethodRegex;
    }

    String getFullMethodRegex() {
        return this.fullMethodRegex;
    }

    void setMethodNameRegex(String methodNameRegex){
        this.methodNameRegex = methodNameRegex;
    }

    String getMethodNameRegex() {
        return this.methodNameRegex;
    }

    void setMethodReturnRegex(String methodReturnRegex){
        this.methodReturnRegex = methodReturnRegex;
    }

    String getMethodReturnRegex() {
        return this.methodReturnRegex;
    }


    protected void setLeakTag(String leakTag){
        this.leakTag = leakTag;
    }

    protected String getLeakTag(){
        return this.leakTag;
    }

     void setCutAwayTag(String cutAwayTag){
        this.cutAwayTag = cutAwayTag;
    }

    protected void setPattern(Pattern pattern){
        this.pattern = pattern;
    }

    protected String getFileAsSingleLine(){
        return this.fileAsSingleLine;
    }

    protected void setSinkMethodRegex(String sinkMethodRegex) {
        this.sinkMethodRegex = sinkMethodRegex;
    }

    public String getSinkMethodRegex(){
        return this.sinkMethodRegex;
    }

    /**
     * Returns a string representation of the Parsers object containing the matcher, used regex and found leaks.
     * @return String representation of the parser.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Leak Indication Regex: ").append(leakTag).append("\n");
        builder.append("Leaks").append(leaksFromReport);
        return builder.toString();
    }
}
