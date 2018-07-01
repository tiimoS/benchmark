package shell.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Responsible for gathering the Output stream to the console of the running analysis and showing it to the user.
 * It does so by using the consumer pattern and by submitting the StreamHunter to a SingleThreadExecutor.
 * The StreamHunter is initialized with the process' input stream and the System.out::println as a consumer.
 * The StreamHunter uses BufferedReader, InputStreamReader, InputStream to gather the input streams and the consumer
 * function to show it in our console.
 *
 * @author Timo Spring
 */
public class StreamHunter implements Runnable{

    private InputStream inputStream;
    private Consumer<String> consumer;

    public StreamHunter(InputStream inputStream, Consumer<String> consumer){
        this.inputStream = inputStream;
        this.consumer = consumer;

    }

    /**
     * Creates new BufferedReader to which we are passing our console output fileAsSingleLine by fileAsSingleLine. So the console output
     * is triggered by the analysis tool in the shell, collected by our BufferedReader and transferred fileAsSingleLine by fileAsSingleLine
     * to our console.
     *
     * Overrides the java.lang.Runnable Interface.
     */
    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
    }
}
