package parser;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for parsing iccta tool results files and returning leaks that are indicated inside this results file (.txt)
 * Defines the regex required to find the desired components inside the results file such as leak description, class toolName,
 * method toolName etc. Furthermore, it overrides the method to find the component toolName inside the leak description.
 *
 * Since the iccta results file has the same structure as the flowdroid results file, we can simply extend the flowdroid
 * parser and call its constructor.
 *
 * The FlowdroidParser extends the Parsers class that implements the IParser Interface.
 *
 * @author Timo Spring
 */
public class IcctaParser extends FlowDroidParser {
    private Pattern pattern;
    private Matcher matcher;
    private String className;


    public IcctaParser(File file)
    {
        super(file);
    }

    @Override
    protected String[] findMethodName(String finding) {
        String regex = "(?<=" + className.replaceAll("\\$", "\\\\\\$") + ": )(\\w|\\.)+ (\\w|[<>])+\\([^)]*\\)";
        setupMatcher(finding, regex);
        String method = "";
        while (matcher.find()){
            method = matcher.group();
        }
        String[] fullMethod = method.split(" ");
        String[] methodName = {fullMethod[1], fullMethod[0]};
        return methodName;
    }


    @Override
    protected String findClassName(String finding) {
        shortenFinding(finding);
        return className;

    }

    private void shortenFinding(String finding) {
        String regex = "(?<=in <).*(?=\\>)";
        setupMatcher(finding, regex);
        if(matcher.find()) {
            String match1 = matcher.group();
            if (matcher.find()) {
                getAndCompareAllOccurrences(finding, match1);
                return;
            } else {
                String[] className3 = match1.split(":");
                className = className3[0];
            }
        }else className = ""; return;
    }

    private void getAndCompareAllOccurrences(String finding, String match1) {
        String beginning = match1.split("\\.")[0];
        String regex = "(?<=<)" + beginning + "\\.(\\w|\\.)+";
        setupMatcher(finding, regex);
        ArrayList<String> candidates = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group();
            boolean exists = false;
            selectCandidates(candidates, match, exists);
        }
        compareClassNames(candidates);
        return;
    }

    private void setupMatcher(String finding, String regex) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(finding);
    }

    private void selectCandidates(ArrayList<String> candidates, String match, boolean exists) {
        for(int i = 0; i < candidates.size(); i++){
            if(match.equals(candidates.get(i))){
                exists = true;
                break;
            }
        }
        if(!exists) {
            candidates.add(match);
        }
    }

    private void compareClassNames(ArrayList<String> candidates) {
        Iterator<String> iterator = candidates.iterator();
        String className1 = candidates.get(0);
        while (iterator.hasNext()) {
            String className2 = iterator.next();
            if (!className1.equals(className2)) {
                className = className2;
            } else {
                className = className1;
            }
        }
        return;
    }

}