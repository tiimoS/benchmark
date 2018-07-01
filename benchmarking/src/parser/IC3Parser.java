package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for parsing ic3 tool results files and returning leaks that are indicated inside this results file (.log)
 * Defines the regex required to find the desired components inside the results file such as leak description, class toolName,
 * method toolName etc. Furthermore, it overrides the method to find the method toolName inside the leak description.
 *
 * It extends the Parsers class that implements the IParser Interface.
 *
 * @author Timo Spring
 */
public class IC3Parser extends Parsers{

    private Pattern pattern;
    private Matcher matcher;

    /**
     * Defines the regex required to parse the ic3 results files and prepares the pattern for the matching.
     * @param file - ic3 tool results file (.txt) describing the found leaks
     */
    public IC3Parser(File file){
        super(file);
        setLeakTag("(?<=\\*\\*\\*\\*\\*Result\\*\\*\\*\\*\\*)[\\s\\S]*(?=PATH)");
        setCutAwayTag("");
        setClassNameRegex(".*\\..*(?=\\/)");
        setMethodReturnRegex("\\w+(?= )");
        setMethodNameRegex("(?<= ).*");
        setFullMethodRegex("(?<=\\/).*(?= :)");
        setSinkMethodRegex("(?<=<).*(?=>)");
        pattern = Pattern.compile(getLeakTag());
    }

    /**
     * Finds the section describing the leaks inside the results file. It does so by using the regex with look ahead and
     * look behind and recursive function calls. The whole section containing the list of leaks is stored in the arrayList
     * of Strings at the first index. We then use findSubLeaks to find the single leak descriptions inside the leak
     * section.
     * @return ArrayList of Strings containing the whole leak section at the first index
     */
    @Override
    protected ArrayList<String> findLeaksInReport() {
        ArrayList<String> leaksFromReport = new ArrayList<>();
        String fileAsSingleLine = getFileAsSingleLine();
        String leakTag = getLeakTag();
        pattern = Pattern.compile(leakTag);
        matcher = pattern.matcher(fileAsSingleLine);
        ArrayList<String> findingsPart = new ArrayList<>();

        if(matcher.find()){
            findingsPart.add(matcher.group());
            leaksFromReport = findSubLeaks(findingsPart);
        }
        return leaksFromReport;
    }

    /**
     * Finds the section describing the leaks inside the results file. It does so by using the regex with look ahead and
     * look behind and recursive function calls. For each found leak description a new String is added to the
     * ArrayList of found leaks.
     * @param findingsPart - ArrayList of Strings containing the whole leak section of the results file at the first index.
     * @return ArrayList of Strings with a String for each found leak.
     */
    private ArrayList<String> findSubLeaks(ArrayList<String> findingsPart) {
        ArrayList<String> leaks = new ArrayList<>();
        String leakRegex = "(?<=\\n).*\\nComponents: .*\\n.*\\n.*";
        pattern = Pattern.compile(leakRegex);
        for(String leak: findingsPart){
            matcher = pattern.matcher(leak);
            while(matcher.find()){
                leaks.add(matcher.group());
            }
        }

        return leaks;
    }

    @Override
    protected String[] findSinkMethod(String finding){
        String[] shortedFinding = findMatches(finding, getSinkMethodRegex()).split(":");
        String[] fullMethod = shortedFinding[1].split(" ");
        String[] method = {fullMethod[2], fullMethod[1]};
        return method;
    }


}
