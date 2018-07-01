package parser;


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for parsing flowdroid tool results files and returning leaks that are indicated inside this results file (.txt)
 * Defines the regex required to find the desired components inside the results file such as leak description, class toolName,
 * method toolName etc. Furthermore, it overrides the method to find the method toolName inside the leak description.
 *
 * It extends the Parsers class that implements the IParser Interface.
 *
 * @author Timo Spring
 */
public class FlowDroidParser extends Parsers {
    protected Pattern flowdroidPattern;
    private Matcher flowdroidMatcher;

    /**
     * Defines the regex required to parse the flowdroid results files and prepares the pattern for the matching.
     * @param file - flowdroid tool results file (.xml) describing the found leaks
     */
    public FlowDroidParser(File file){
        super(file);
        setLeakTag("Found a flow");
        setCutAwayTag("Maximum memory");

        setClassNameRegex("(?<=\\(in \\<).*(?=\\: )");
        setFullMethodRegex("(?<=\\(in \\<).*(?=\\>\\))");
        setMethodNameRegex("(?<= ).*");
        setMethodReturnRegex(".*(?= )");
        setSinkMethodRegex("(?<=: ).*(?=>)");

        pattern = Pattern.compile(getLeakTag());

    }

    /**
     * Finds the method toolName from the leak description. We first have to reduce the leak description from one side,
     * and then parse with another regex from the other side to get the method toolName.
     * We use lookeahead and look behind regex here.
     * @param finding - String description of the leak.
     * @return method toolName as a string
     */
   /* @Override
    protected String[] findMethodName(String finding) {
        String[] shortedFinding = findMatches(finding, getFullMethodRegex()).split(" ");
        String[] method = {shortedFinding[2], shortedFinding[1]};
        return method;
    }*/


    @Override
    protected String[] findMethodName(String finding) {
        String shortedFinding = findLastOccurrence(finding, getFullMethodRegex());
        String[] classAndMethod = shortedFinding.split(" ");
        String[] method = {classAndMethod[2], classAndMethod[1]};
        return method;
    }


    @Override
    protected String findClassName(String finding) {
        String className = findLastOccurrence(finding, getClassNameRegex());
        return className;

    }

    protected String findLastOccurrence (String finding, String regex){
        String fullMethod = "";
        flowdroidPattern = Pattern.compile(regex);
        flowdroidMatcher = flowdroidPattern.matcher(finding);
        int i = 0;
        while(flowdroidMatcher.find()){
            fullMethod = flowdroidMatcher.group();
        }
        return fullMethod;
    }

}
