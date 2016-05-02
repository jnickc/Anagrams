
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for testing anagrams
 * Created by Nicolai Cebanov on 3/11/16.
 */
public class AnagramsTest {

    private static String TEST_FILE_PATH = "src/main/resources/sample.txt";
    private static int WORDS_COUNT;
    private static final int TEST_REPEAT_COUNT = 1;


    /**
     * Main method of app, generates test data, runs execution
     *
     * @param args - in args for application
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if(args.length > 0){
            if(args[0] != null){
                TEST_FILE_PATH = args[0];
            }
            if(args[1] != null){
                WORDS_COUNT = Integer.parseInt(args[1]);
            }
            Utils.preprateTestData(TEST_FILE_PATH, WORDS_COUNT);
        } else {
            System.out.println("Running app with default sample.txt file");
            System.out.println("For testing app with other test file add to app arguments file path," +
                    "\n for testing with random generated file add to app arguments words number to generate ");

        }

        runWithConcurrentHashMap();
        runWithChronicleMap();

    }


    /*
     * Runs anagram search with concurrentHashMap as key value store for anagrams
     */
    private static void runWithConcurrentHashMap(){

        double ms = 0;
        Anagram anagram = new Anagram();
        for (int i = 0; i < TEST_REPEAT_COUNT; i++) {
            anagram.setSortedWordsMap(new ConcurrentHashMap<>());
            ms += anagram.exec(TEST_FILE_PATH);

            Utils.printResults(anagram);

        }
        System.out.println("Total time in ms for cuncurrentHashMap = " + ms);

    }

    /*
     * Runs anagram search with chronicle map as key value store for anagrams
     */
    private static void runWithChronicleMap(){
        double ms = 0;
        Anagram anagram = new Anagram();

        for (int i = 0; i < TEST_REPEAT_COUNT; i++) {
            anagram.setSortedWordsMap(ChronicleMapBuilder
                    .of(String.class, StringBuffer.class)
                    .entries(Utils.countLinesInFile(TEST_FILE_PATH))
                    .create());
            ms += anagram.exec(TEST_FILE_PATH);
            Utils.printResults(anagram);
        }

        System.out.println("Total time in ms for chronicleMap = " + ms);
    }

}
