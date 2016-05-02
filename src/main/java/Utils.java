import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains util methods for Anagram app
 * <p>
 * Created by Nicolai Cebanov on 3/11/16.
 */
public class Utils {

    /**
     * Generates words and writes them to output test file
     *
     * @param wordsCount
     * @throws FileNotFoundException
     */
    protected static void preprateTestData(String testFilePath, int wordsCount) throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(testFilePath)) {
            String[] testWords = generateRandomWords(wordsCount);
            for (String word : testWords) {
                out.println(word);
            }
        }

    }

    /**
     * Generate random words with max length 10 chars
     *
     * @param wordsCount - number of words to generate
     * @return - generated words array
     */
    protected static String[] generateRandomWords(int wordsCount) {
        String[] randomStrings = new String[wordsCount];
        Random random = new Random();
        for (int i = 0; i < wordsCount; i++) {
            char[] word = new char[random.nextInt(8) + 3];
            for (int j = 0; j < word.length; j++) {
                word[j] = (char) ('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    /**
     * Read file to set, filtering duplicates
     *
     * @param testFilePath - path to file
     * @return set of words
     */
    protected static Set<String> readFile(String testFilePath) {
        Set<String> wordSet = new HashSet<>();
        String sCurrentLine;


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(testFilePath));

            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                wordSet.add(sCurrentLine);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordSet;
    }

    /**
     * Counts number of lines in file
     *
     * @param testFilePath - path to test file
     * @return number of lines in file
     */
    protected static int countLinesInFile(String testFilePath) {

        int result = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(testFilePath));

            while (bufferedReader.readLine() != null) result++;

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected static void printResults(Anagram anagram) {
        System.out.println("Size = " + anagram.getSortedWordsMap().values().stream().filter(string -> string.toString().contains(" ")).collect(Collectors.toList()).size());

        List<String> anagramsList = new ArrayList<>();

        anagram.getSortedWordsMap().values().stream()
                .filter(string -> string.toString().contains(" "))
                .map(sBuffer -> sBuffer.toString()).forEach((anagramString) -> {
            String[] sortedAnagrams = anagramString.split(" ");
            Arrays.sort(sortedAnagrams);
            anagramsList.add(StringUtils.join(sortedAnagrams, " "));
        });


        anagramsList.stream().sorted().forEach(System.out::println);

    }
}

