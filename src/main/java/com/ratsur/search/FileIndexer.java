package com.ratsur.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/****
 * This file has the core logic for Indexing the file.
 * It creates an HashMap(fileWordsMap) of all the files in the input dir as key
 * and a Map of words and word count as value.
 * Time Complexity O(nxmxk) where n is the number of files in the dir
 *     m the number of lines and k number of words
 * Space Complexity
 *     Requires space to hold the file names and distinct words inside the files
 *     O(nxm) where m is the number of distinct words. keeps the count of the distinct words in each file
 */
public class FileIndexer {

    private Map<String, Map<String, Integer>> fileWordsMap;
    static final String DEFAULT_WORD_DELIMITER = " ";
    static final String DEFAULT_FILE_EXTENSION= ".txt";
    static final int DEFAULT_RESULT_COUNT = 10;
    private int resultCount;
    private String wordDelimiter;
    private final File dir;
    private String ext;

    public FileIndexer(final File inputDir) {
        dir = inputDir;
        resultCount = DEFAULT_RESULT_COUNT;
        wordDelimiter = DEFAULT_WORD_DELIMITER;
        ext = DEFAULT_FILE_EXTENSION;
    }

    public FileIndexer(final File inputDir, int resultCount) {
        dir = inputDir;
        this.resultCount = resultCount;
        ext = DEFAULT_FILE_EXTENSION;
    }

    public FileIndexer(final File inputDir, int resultCount, String wordDelimiter) {
        dir = inputDir;
        this.resultCount = resultCount;
        this.wordDelimiter = wordDelimiter;
        ext = DEFAULT_FILE_EXTENSION;
    }

    /**
     * This methods indexes the files in dir
     * Currently it doesn't support files in directories inside the dir
     * Consider file with names ending with *.txt to be a text file
     * @return Number of text files indexed in the given directory
     */
    public int indexDir() {
        fileWordsMap = new HashMap<>();
        for (final File file : dir.listFiles()) {
            if (!file.isDirectory() && file.getName().endsWith(ext)) {
                fileWordsMap.put(file.getName(), readFile(file));
            }
        }
        return fileWordsMap.size();
    }

    /**
     * This method is used to search in the directory which is already indexed
     * @param words
     * @return an Object of search results which contains the top 10 results by default
     */
    public SearchResults search(String words) {
        if (fileWordsMap == null)
            throw new IllegalArgumentException("Please index the files before searching");
        if (fileWordsMap.size() < 1)
            throw new IllegalArgumentException("No txt files present in the input directory");
        String[] wordArray = words.split(wordDelimiter);
        if(wordArray.length<1)
            throw new IllegalArgumentException("No words to search, please specify at least one word to search in indexed directory");
        //to be or not to be
        //to 2, not 1 or 1
        //to be o be 2r abc def ghi jkl be be -> to 1, be 3, abc 1, def 1
        //O(nxm)

        Map<String,Integer> wordCountMap = getWordCountMap(wordArray);

        int numOfWords = wordArray.length;
        SearchResults results = new SearchResults();
        for (Map.Entry<String, Map<String, Integer>> entry : fileWordsMap.entrySet()) {
            int count = 0;
            for (Map.Entry<String,Integer> word : wordCountMap.entrySet()) {
                count += Math.min(entry.getValue().getOrDefault(word.getKey(),0),word.getValue());
            }
            if(count>0)
            {
                results.add(new SearchResult(entry.getKey(),((float)count/numOfWords)*100));
            }
        }

        return results;
    }

    private Map<String, Integer> getWordCountMap(String[] words) {
        Map<String, Integer> wordCountMap = new HashMap<>();
        for (String word : words)
            wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        return wordCountMap;
    }


    private Map<String, Integer> readFile(File file) {
        Map<String, Integer> wordCountMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(wordDelimiter);
                for (String word : words) {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (Exception e) {
            //todo log exception in reading file
        }
        return wordCountMap;
    }


}
