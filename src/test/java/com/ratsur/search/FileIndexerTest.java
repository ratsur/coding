package com.ratsur.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FileIndexerTest {

    final static String path = "src\\test\\resources\\files\\";

    @BeforeAll
    static void setUp() {
        int i = 0;
        while (i < 500) {
            try {
                i++;
                File file = new File(path);
                FileWriter myWriter = new FileWriter(path + "filename" + i + ".txt", false);
                myWriter.write("Files in Java might be tricky, but it is fun enough!\n");
                myWriter.write("abc def ghi jkl\n");
                myWriter.close();
                //System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    @Test
    void indexDir() {
        FileIndexer fi = new FileIndexer(new File(path));
        int numOfFiles = fi.indexDir();
        Assertions.assertEquals(503, numOfFiles);
    }

    @Test
    void searchDir() {
        FileIndexer fi = new FileIndexer(new File(path));
        fi.indexDir();
        System.out.println("Searching abc def ghi jkl");
        SearchResults results = fi.search("abc def ghi jkl");
        Assertions.assertEquals(10, results.size());
        results.display();
        System.out.println("Searching to some to be");
        results = fi.search("to some to be");
        Assertions.assertEquals(10, results.size());
        results.display();
        System.out.println("Searching to be not");
        results = fi.search("to be not");
        Assertions.assertEquals(10, results.size());
        results.display();
        results = fi.search("adsfadsfgasfasdfadsfasdf");
        Assertions.assertEquals(0, results.size());
        results.display();
    }

    @Test
    void searchAllFiles100Percent() {
        FileIndexer fi = new FileIndexer(new File(path));
        fi.indexDir();
        System.out.println("Searching abc def ghi jkl");
        SearchResults results = fi.search("abc def ghi jkl");
        List<SearchResult> expected = new ArrayList<>();
        expected.add(new SearchResult("filename99.txt", 100f));
        expected.add(new SearchResult("filename98.txt", 100f));
        expected.add(new SearchResult("filename97.txt", 100f));
        expected.add(new SearchResult("filename96.txt", 100f));
        expected.add(new SearchResult("filename95.txt", 100f));
        expected.add(new SearchResult("filename94.txt", 100f));
        expected.add(new SearchResult("filename93.txt", 100f));
        expected.add(new SearchResult("filename92.txt", 100f));
        expected.add(new SearchResult("filename91.txt", 100f));
        expected.add(new SearchResult("filename90.txt", 100f));
        List<SearchResult> actual = results.getResults();
        compareListContnets(expected, actual);
        results = fi.search("adsfadsfgasfasdfadsfasdf");
        Assertions.assertEquals(0, results.size());
        results.display();
    }

    @Test
    void notFoundInSearch() {
        FileIndexer fi = new FileIndexer(new File(path));
        fi.indexDir();
        List<SearchResult> expected = new ArrayList<>();
        List<SearchResult> actual = fi.search("adsfadsfgasfasdfadsfasdf").getResults();
        compareListContnets(expected, actual);
    }

    @Test
    void searchAllFilesWithVaraiblePercent() {
        FileIndexer fi = new FileIndexer(new File(path));
        fi.indexDir();
        System.out.println("Searching to some to be");
        SearchResults results = fi.search("to some to be");
        List<SearchResult> expected = new ArrayList<>();
        expected.add(new SearchResult("file2.txt", 75f));
        expected.add(new SearchResult("file1.txt", 75f));
        expected.add(new SearchResult("filename99.txt", 25f));
        expected.add(new SearchResult("filename98.txt", 25f));
        expected.add(new SearchResult("filename97.txt", 25f));
        expected.add(new SearchResult("filename96.txt", 25f));
        expected.add(new SearchResult("filename95.txt", 25f));
        expected.add(new SearchResult("filename94.txt", 25f));
        expected.add(new SearchResult("filename93.txt", 25f));
        expected.add(new SearchResult("filename92.txt", 25f));
        List<SearchResult> actual = results.getResults();
        compareListContnets(expected, actual);

    }

    private void compareListContnets(List<SearchResult> expected, List<SearchResult> actual) {
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getFileName(), actual.get(i).getFileName());
            Assertions.assertEquals(expected.get(i).getMatchPercent(), actual.get(i).getMatchPercent());
        }
    }


}