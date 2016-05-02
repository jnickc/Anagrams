import java.util.*;

/**
 * Class for searching anagrams in given file
 *
 * Created by Nicolai Cebanov on 3/11/16.
 */
public class Anagram {


    private static final int CPU_NUMBER = Runtime.getRuntime().availableProcessors()-2;

    private Map<String, StringBuffer> sortedWordsMap;

    /*
     * Reads the given file and searches anagrams in the set of words
     */
    public double exec(String fileToRead) {

        try {
            Set<String> wordSet = Utils.readFile(fileToRead);
            String[] words = wordSet.toArray(new String[wordSet.size()]);
            return searchAnagrams(words, CPU_NUMBER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Splits given array to number of availabe cores
     * and sends this chunks to compute by threads
     * @param words - array of words to search anagrams
     * @param cpuNumber - number of available cores
     * @return compute time in milliseconds
     * @throws InterruptedException
     */
    private double searchAnagrams(String[] words, int cpuNumber) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        int chunkLenght = words.length / cpuNumber;
        int lastChank = chunkLenght + (words.length % cpuNumber);
        AnagramFinder[] anagramFinders = new AnagramFinder[cpuNumber];
        for (int i = 0; i< cpuNumber; i++) {
            if(i == (cpuNumber-1)) {
                anagramFinders[i] = new AnagramFinder(i * chunkLenght, i * chunkLenght + lastChank, words);
            }else{
                anagramFinders[i] = new AnagramFinder(i * chunkLenght, i * chunkLenght + chunkLenght, words);
            }
            anagramFinders[i].start();
        }

        for (int i = 0; i < cpuNumber; i++) {
            anagramFinders[i].join();
        }

        return (System.currentTimeMillis() - startTime);
    }

    /*
        Class implements anagram search algorithm
     */
    class AnagramFinder extends Thread {

        private int startOffset;
        private int endOffset;
        private String[] words;

        public AnagramFinder(int startOffset, int endOffset, String[] words) {
            this.startOffset = startOffset;
            this.endOffset = endOffset;
            this.words = words;
        }

        /**
         * Loops in given chunk of array and searches anagrams,
         * found anagrams adds to sortedWordsMap
         * Executes method in new thread
         */
        @Override
        public void run() {
            String sorted;
            StringBuffer value;
            for (int i = startOffset; i <endOffset; i++) {
                sorted = sortChars(words[i]);
                synchronized (sortedWordsMap) {

                    if (!sortedWordsMap.containsKey(sorted)) {
                        sortedWordsMap.put(sorted, new StringBuffer(words[i]));
                    } else {
                        value = sortedWordsMap.get(sorted);
                        sortedWordsMap.put(sorted, value.append(" ").append(words[i]));
                    }
                }
            }

        }

        /**
         * Sorts chars of given word in asc order
         * @param word - to sort
         * @return - sorted word
         */
        private String sortChars(String word) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            return new String(chars);

        }

    }

    public Map<String, StringBuffer> getSortedWordsMap() {
        return sortedWordsMap;
    }

    public void setSortedWordsMap(Map<String, StringBuffer> sortedWordsMap) {
        this.sortedWordsMap = sortedWordsMap;
    }
}
