package parser;

import leaks.Leak;
import tool.ITool;

import java.util.ArrayList;


/**
 * Responsible for finding the leaks inside a results file, creating Leak objects for each found leak and returning those objects.
 * Also provides the possibility to get a string representation of the parser object.
 */
public interface IParser {

    /**
     * Parses the results file and searches for the section where the leaks are described. Then we create a new String
     * for each leak mentioned in the results file and store it in an ArrayList of strings. We then parse each leak String
     * for component toolName, class toolName and method toolName mentioned in the description text. After we have all required information
     * we create the desired Leak Objects.
     *
     * @return ArrayList of Leak Objects containing the component toolName, class toolName and method toolName as Strings of the
     * parsed leaks.
     * @param name
     */
    ArrayList<Leak> getLeaks(ITool tool);

    /**
     * Returns a string representation of the Parsers object containing the matcher, used regex and found leaks.
     * @return String representation of the parser.
     */
    String toString();
}
