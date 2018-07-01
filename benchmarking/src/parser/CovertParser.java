package parser;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Responsible for parsing cover tool results files and returning leaks that are indicated inside this results file (.xml)
 * Defines the regex required to find the desired components inside the results file such as leak description, class toolName,
 * method toolName etc. Furthermore, it overrides the method to find the component toolName inside the leak description.
 *
 * It extends the Parsers class that implements the IParser Interface.
 *
 * @author Timo Spring
 */
public class CovertParser extends Parsers{

    /**
     * Defines the regex required to parse the covert results files and prepares the pattern for the matching.
     * @param file - covert tool results file (.xml) describing the found leaks
     */
    public CovertParser(File file) {
        super(file);
        setLeakTag("<vulnerability>");
        setCutAwayTag("</vulnerabilities>");

        setClassNameRegex("(?<=<description>).*(?=:)");
        setFullMethodRegex("(?<=: ).*(?=<\\/description>)");
        setMethodNameRegex("(?<= ).*");
        setMethodReturnRegex("\\w+(?= )");
        setSinkMethodRegex("(?<=: ).*(?=<\\/description>)");

        pattern = Pattern.compile(getLeakTag());

    }
}
