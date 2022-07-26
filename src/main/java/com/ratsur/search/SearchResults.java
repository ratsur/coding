package com.ratsur.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * It internally uses a PriorityQueue of searchResult to hold only max results as required
 * The getResults and display msg internally uses a list to return the correct order
 * uses SearchResult as the pojo to hold multiple results in PriorityQueue
 */
public class SearchResults {

    public static final int DEFAULT_MAX_SIZE = 10;
    private final int MAX_SIZE;
    PriorityQueue<SearchResult> searchResults;

    public SearchResults() {
        MAX_SIZE = DEFAULT_MAX_SIZE;
        searchResults = new PriorityQueue<>();
    }

    public SearchResults(int size) {
        MAX_SIZE = size;
        searchResults = new PriorityQueue<>();
    }

    public SearchResults(int size, Comparator<SearchResult> src) {
        MAX_SIZE = size;
        searchResults = new PriorityQueue<>(src);
    }


    public void add(SearchResult searchResult) {
        if (searchResults.size() < MAX_SIZE) {
            searchResults.add(searchResult);
        } else if (searchResult.compareTo(searchResults.peek()) > 0) {
            searchResults.poll();
            searchResults.add(searchResult);
        }
    }

    public List<SearchResult> getResults() {
        if (searchResults.isEmpty())
            System.out.println("no matches found");
        List<SearchResult> list = new ArrayList<>();
        List<SearchResult> outlist = new ArrayList<>();
        while (!searchResults.isEmpty())
            list.add(searchResults.poll());
        for (int i = list.size() - 1; i >= 0; i--)
            outlist.add(list.get(i));
        return outlist;
    }


    public void display() {
        if (searchResults.isEmpty())
            System.out.println("no matches found");
        List<SearchResult> list = new ArrayList<>();
        while (!searchResults.isEmpty())
            list.add(searchResults.poll());
        for (int i = list.size() - 1; i >= 0; i--)
            System.out.println(list.get(i));
    }


    public int size() {
        return searchResults.size();
    }
}
