package com.ratsur.search;

/**
 *
 * Simple pojo class to hold filename and ranking. Implements comparable to be compared based on ranking
 * If the ranking is same then uses alphabetical order of filename.
 *
 */
public class SearchResult implements Comparable<SearchResult> {
    private String fileName;
    private float matchPercent;

    public SearchResult() {
    }

    public SearchResult(String fileName, float matchPercent) {
        this.fileName = fileName;
        this.matchPercent = matchPercent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public float getMatchPercent() {
        return matchPercent;
    }

    public void setMatchPercent(float matchPercent) {
        this.matchPercent = matchPercent;
    }

    @Override
    public String toString() {
        return fileName + ':' + matchPercent + '%';
    }

    @Override
    public int compareTo(SearchResult otherResult) {
        int ans = Float.compare(getMatchPercent(),otherResult.getMatchPercent());
        if (ans == 0) {
            return fileName.compareTo(otherResult.fileName);
        }
        return ans;

    }
}
